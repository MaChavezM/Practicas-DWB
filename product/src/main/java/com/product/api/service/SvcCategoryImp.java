package com.product.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    private RepoCategory repoCategory;

    @Override
    public List<Category> getCategories() {
        return repoCategory.getCategories();
    }
}