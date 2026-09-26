package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

/**
 * Controlador para manejar las solicitudes relacionadas con las categorías.
 * 
 */
@RestController

public class CtrlCategory {

    @Autowired
    SvcCategory svc;

    @GetMapping("/category")
    public List<Category> getCategories() {
        return svc.getCategories();
    }
}
