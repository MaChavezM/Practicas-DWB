package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.api.entity.ProductImage;

@Repository
public interface RepoProductImage extends JpaRepository<ProductImage, Integer> {

    // Método para obtener las imágenes de un producto por su ID
    List<ProductImage> findByProductId(Integer productId);

}