package com.watchstore.service;

import com.watchstore.model.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
}
