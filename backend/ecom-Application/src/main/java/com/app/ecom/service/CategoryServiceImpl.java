package com.app.ecom.service;


import com.app.ecom.exceptions.APIException;
import com.app.ecom.exceptions.ResourceNotFoundException;
import com.app.ecom.model.Category;
import com.app.ecom.payload.CategoryRequest;
import com.app.ecom.payload.CategoryResponse;
import com.app.ecom.repository.CategoryReopsitory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{



    @Autowired
    private CategoryReopsitory categoryReopsitory;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryReopsitory.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();

        if(categories.isEmpty())
            throw new APIException("No category created till now, Please create a category");
            List<CategoryRequest> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryRequest.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryRequest createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);

        Category CategoryFromDb = categoryReopsitory.findByCategoryName(category.getCategoryName());
        if (CategoryFromDb != null)
            throw new APIException("Category with category name " + category.getCategoryName() + " already exists");
        Category savedCategory = categoryReopsitory.save(category);
        return modelMapper.map(savedCategory, CategoryRequest.class);
    }

    @Override
    public CategoryRequest deleteCategory(Long categoryId) {

        Category category = categoryReopsitory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId ));

        categoryReopsitory.delete(category);
        return modelMapper.map(category, CategoryRequest.class);
    }

    @Override
    public CategoryRequest updateCategory(CategoryRequest categoryRequest , Long categoryId) {

        Category savedCategory = categoryReopsitory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


        Category category = modelMapper.map(categoryRequest, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryReopsitory.save(category);
        return modelMapper.map(savedCategory, CategoryRequest.class);
    }
    }

