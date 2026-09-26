package com.product.api.entity;

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
    private Integer category_id;

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
        this.category_id = category_id;
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

    public Integer getCategory_id() {
        return category_id;
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

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    /**
     * Devuelve una representación en cadena de la categoría.
     *
     * @return Una cadena que representa la categoría.
     */

    @Override
    public String toString() {
        return "{" + category_id + ", " + category + ", " + tag + ", " + parentCategoryId + ", " + status + "}";
    }

}
