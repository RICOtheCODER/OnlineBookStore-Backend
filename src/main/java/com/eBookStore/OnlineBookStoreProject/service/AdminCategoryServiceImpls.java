package com.eBookStore.OnlineBookStoreProject.service;


import com.eBookStore.OnlineBookStoreProject.dto.CategoryDto;
import com.eBookStore.OnlineBookStoreProject.model.Category;
import com.eBookStore.OnlineBookStoreProject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpls implements AdminCategoryService {
    @Autowired
    private final CategoryRepository repository;

    public Category createCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return repository.save(category);
    }

    public List<Category> getAllCategories(){
        return repository.findAll();
    }

    @Override
    public String findNameFromId(Long id) {
        Optional<Category> category=repository.findById(id);
        return category.get().getName();
    }
}
