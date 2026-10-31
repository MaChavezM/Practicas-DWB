package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoCategoryIn;
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
    public ResponseEntity<List<Category>> getCategories() {
        return svc.getCategories();
    }
<<<<<<< Updated upstream
=======

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return ResponseEntity.ok(svc.findActive());
    }

    @GetMapping("/{id}/childs")
    public ResponseEntity<List<Category>> findChilds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(svc.findChilds(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn in) {
        svc.create(in);
        return ResponseEntity.ok("La Categoría creada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer category_id, @Valid @RequestBody DtoCategoryIn in) {
        svc.update(in, category_id);
        return ResponseEntity.ok("La Categoría actualizada correctamente");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer category_id) {
        svc.enable(category_id);
        return ResponseEntity.ok("La Categoría habilitada correctamente");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer category_id) {
        svc.disable(category_id);
        return ResponseEntity
                .ok("La Categoría ha sido deshabilitada correctamente");
    }

>>>>>>> Stashed changes
}
