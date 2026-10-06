package com.product.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.product.api.entity.Category;
import com.product.api.exception.ApiException;
import com.product.api.exception.DBAccessException;
import com.product.api.repository.RepoCategory;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    private RepoCategory repo;

    @Override
    public List<Category> findAll() {
        try {
            return repo.findAllByOrderByCategoryIdAsc();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        try {
            return repo.findByStatusOrderByCategoryAsc(1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findChilds(Integer id) {
        validateId(id);
        try {
            return repo.findByParentCategoryIdAndStatusOrderByCategoryAsc(id, 1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn in) {
        validateParent(in.getParentCategoryId(), null);
        try {
            Category category = new Category();
            category.setCategory(in.getCategory().trim());
            category.setTag(in.getTag().trim());
            category.setParentCategoryId(in.getParentCategoryId());
            category.setStatus(1);
            repo.save(category);
        } catch (DataAccessException e) {
            handleSaveException(e); // Se crea un método privado para manejar la excepción }
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        Category category = validateId(id);
        validateParent(in.getParentCategoryId(), id);
        try {
            category.setCategory(in.getCategory().trim());
            category.setTag(in.getTag().trim());
            category.setParentCategoryId(in.getParentCategoryId());
            repo.save(category);
        } catch (DataAccessException e) {
            handleSaveException(e);
        }
    }

    @Override
    public void enable(Integer id) {
        Category category = validateId(id);

        // Una categoría con padre solo puede activarse si el padre está activo.
        if (category.getParentCategoryId() != null) {
            Category parent = repo.findById(category.getParentCategoryId()).orElse(null);
            if (parent == null || (1 != parent.getStatus()))
                throw new ApiException(HttpStatus.CONFLICT,
                        "No se puede activar la categoría porque su categoría padre está desactivada");
        }
        updateStatus(id, 1);
    }

    @Override
    public void disable(Integer id) {
        validateId(id);
        try {
            if (repo.existsByParentCategoryIdAndStatus(id, 1))
                throw new ApiException(HttpStatus.CONFLICT,
                        "No se puede desactivar la categoría porque tiene categorías hijas");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
        updateStatus(id, 0);
    }

    private void updateStatus(Integer id, Integer status) {
        try {
            repo.updateStatus(id, status);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /** Metodos auxiliares */

    /**
     * Valida que el id de la categoría exista y la devuelve.
     */
    private Category validateId(Integer id) {
        try {
            return repo.findById(id)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe"));
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**
     * Valida el padre: debe existir con estatus 1, no puede ser la misma categoría
     * ni uno de sus descendientes (evita ciclos).
     *
     * @param parentId id del padre (null si no tiene padre)
     * @param selfId   id de la categoría que se actualiza (null al crear)
     */
    private void validateParent(Integer parentId, Integer selfId) {
        if (parentId == null)
            return;

        if (parentId.equals(selfId))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");

        try {
            Category parent = repo.findById(parentId).orElse(null);
            if (parent == null || 1 != parent.getStatus())
                throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe o no está activa");

            if (selfId != null) {
                Set<Integer> visited = new HashSet<>();
                Integer current = parent.getParentCategoryId();
                while (current != null && visited.add(current)) {
                    if (current.equals(selfId))
                        throw new ApiException(HttpStatus.BAD_REQUEST,
                                "Una categoría no puede ser padre de una de sus categorías descendientes");
                    current = repo.findById(current).map(Category::getParentCategoryId).orElse(null);
                }
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void handleSaveException(DataAccessException e) {
        String message = e.getMostSpecificCause().getMessage();
        if (message != null && message.contains("ux_category"))
            throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        if (message != null && message.contains("ux_tag"))
            throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
        throw new DBAccessException(e);
    }

}