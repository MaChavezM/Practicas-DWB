package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.product.api.entity.Category;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

        // Derived Query Methods (JPA arma el query a partir del nombre del método)
        List<Category> findAllByOrderByCategoryIdAsc();

        List<Category> findByStatusOrderByCategoryAsc(Integer status);

        List<Category> findByParentCategoryIdAndStatusOrderByCategoryAsc(Integer parentCategoryId, Integer status);

        boolean existsByParentCategoryIdAndStatus(Integer parentCategoryId, Integer status);

        // create y update se hacen con save() desde el servicio.
        @Modifying(clearAutomatically = true, flushAutomatically = true)
        @Transactional
        @Query(value = "UPDATE category SET status = :status WHERE category_id = :category_id", nativeQuery = true)
        void updateStatus(@Param("category_id") Integer categoryId, @Param("status") Integer status);

}
