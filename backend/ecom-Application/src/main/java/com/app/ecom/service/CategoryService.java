package com.app.ecom.service;


import com.app.ecom.model.Category;
import com.app.ecom.payload.CategoryResponse;
import com.app.ecom.payload.CategoryRequest;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryRequest createCategory(CategoryRequest categoryDTO);
    CategoryRequest deleteCategory(Long categoryId);

    CategoryRequest updateCategory(CategoryRequest categoryRequest, Long categoryId);
}
