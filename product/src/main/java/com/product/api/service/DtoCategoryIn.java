package com.product.api.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para crear y actualizar una categoría.
 * El campo parentCategoryId es opcional: null indica que no tiene padre.
 */
public class DtoCategoryIn {

    @JsonProperty("category")
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String category;

    @JsonProperty("tag")
    @NotBlank(message = "El tag de la categoría es obligatorio")
    private String tag;

    @JsonProperty("parent_category_id")
    @JsonAlias("parentCategoryId")
    private Integer parentCategoryId;

    public DtoCategoryIn() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
