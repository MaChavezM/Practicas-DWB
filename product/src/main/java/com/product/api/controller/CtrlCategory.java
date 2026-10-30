package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.DtoCategoryIn;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;

/**
 * Controlador para manejar las solicitudes relacionadas con las categorías.
 * 
 */
@RestController
@RequestMapping("/category")

public class CtrlCategory {

    @Autowired
    SvcCategory svc;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svc.findAll());
    }

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

}
