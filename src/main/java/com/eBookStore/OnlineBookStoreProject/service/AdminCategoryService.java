package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.CategoryDto;
import com.eBookStore.OnlineBookStoreProject.model.Category;

import java.util.List;

public interface AdminCategoryService {
    public Category createCategory(CategoryDto categoryDto);

    public List<Category> getAllCategories();

    public String findNameFromId(Long id);
}
