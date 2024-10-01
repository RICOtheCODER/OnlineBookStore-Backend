package com.eBookStore.OnlineBookStoreProject.controller;

import com.eBookStore.OnlineBookStoreProject.dto.CategoryDto;
import com.eBookStore.OnlineBookStoreProject.model.Category;
import com.eBookStore.OnlineBookStoreProject.service.AdminCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class adminCategoryController {

    @Autowired
    private  AdminCategoryService adminCategoryService;
    Logger logger= LoggerFactory.getLogger(adminCategoryController.class);

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        Category category=adminCategoryService.createCategory(categoryDto);
        //logger.error("category");
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(adminCategoryService.getAllCategories());
    }

//    @GetMapping("/categoryName")
//    public String categorynameFromId(Long id){
//        return adminCategoryService.findNameFromId(id);
//    }
}
