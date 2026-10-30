package com.product.api.service;

import java.util.List;

import com.product.api.entity.Category;

public interface SvcCategory {

  public List<Category> findAll();

  public List<Category> findActive();

  public List<Category> findChilds(Integer id);

  public void create(DtoCategoryIn in);

  public void update(DtoCategoryIn in, Integer category_id);

  public void enable(Integer category_id);

  public void disable(Integer category_id);

}