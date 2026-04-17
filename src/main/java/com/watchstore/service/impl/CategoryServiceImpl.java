package com.watchstore.service.impl;

import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.model.Category;
import com.watchstore.repository.CategoryRepository;
import com.watchstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired private CategoryRepository categoryRepository;

    @Override public Category createCategory(Category category) { return categoryRepository.save(category); }
    @Override public List<Category> getAllCategories() { return categoryRepository.findAll(); }
    @Override public Category getCategoryById(Long id) { 
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found")); 
    }
}
