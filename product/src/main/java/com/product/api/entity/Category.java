package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    /**
     * Clase que representa una categoría.
     * 
     * @author MaChavezM
     * @version 1.0
     */

    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "tag")
    private String tag;
    @Column(name = "parent_category_id")
    private Integer parentCategoryId;
    @Column(name = "status")
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id")
    private Integer categoryId;

    /**
     * Constructor vacio.
     */
    public Category() {

    }

    /**
     * Constructor de la clase Category.
     *
     */

    public Category(String category, String tag, Integer parentCategoryId, Integer status, Integer category_id) {
        super();
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = status;
        this.categoryId = category_id;
    }

    /**
     * Getters
     * 
     * @return El valor del atributo correspondiente.
     */
    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Setters
     * 
     * @param atributo correspondiente a la categoría.
     *
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Devuelve una representación en cadena de la categoría.
     *
     * @return Una cadena que representa la categoría.
     */

    @Override
    public String toString() {
        return "{" + categoryId + ", " + category + ", " + tag + ", " + parentCategoryId + ", " + status + "}";
    }

}
