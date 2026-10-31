package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.exception.ApiException;
import com.product.api.exception.DBAccessException;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;

@Service

public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    RepoProductImage repo;

    @Autowired
    RepoProduct repoProduct;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images}")
    private String uploadImages;

    @Override
    public ResponseEntity<List<ProductImage>> getProductImages(Integer id) {
        try {
            // Validar que el producto exista
            validateProductId(id);
            List<ProductImage> images = repo.findByProductId(id); // Obtener las imágenes del producto desde la base de
                                                                  // datos,
            // Leer cada archivo de imagen y convertirlo a Base64
            for (ProductImage image : images) {
                image.setImage(readProductImageFile(image.getImage()));
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> createProductImage(Integer id, DtoProductImageIn in) {
        try {
            validateProductId(id);

            // Eliminar el prefijo "data:image/png;base64," si existe
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                if (commaIndex != -1) {
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }

            // Decodifica la cadena Base64 a bytes
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

            // Genera un nombre único para la imagen (se asume extensión PNG)
            String fileName = UUID.randomUUID().toString() + ".png";

            // Construye la ruta completa donde se guardará la imagen
            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);

            // Asegurarse de que el directorio exista y escribir el archivo
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            // Guardar la ruta de la imagen en la base de datos
            ProductImage productImage = new ProductImage();
            productImage.setProductId(id);
            productImage.setImage("/product/" + fileName);
            productImage.setStatus(1);
            repo.save(productImage);

            return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> deleteProductImage(Integer id, Integer productImageId) {
        try {
            validateProductId(id);
            if (repo.findById(productImageId).isEmpty())
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe");

            repo.deleteById(productImageId);
            return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private String readProductImageFile(String imageUrl) {
        try {
            // Si el PATH comienza con "/" la eliminamos para obtener la ruta relativa
            if (imageUrl.startsWith("/")) {
                imageUrl = imageUrl.substring(1);
            }
            Path imagePath = Paths.get(uploadDir, uploadImages, imageUrl);

            // Verifica que el archivo exista
            if (!Files.exists(imagePath)) {
                return "";
            }

            // Leer los bytes de la imagen y codificarlos a Base64
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

    private void validateProductId(Integer id) {
        if (repoProduct.findById(id).isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
    }
}
