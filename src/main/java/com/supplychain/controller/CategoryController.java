package com.supplychain.controller;

import com.supplychain.entity.Category;
import com.supplychain.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create Category
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category) {

        Category savedCategory =
                categoryService.saveCategory(category);

        return ResponseEntity.ok(savedCategory);
    }

    // Get All Categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    // Get Category By ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id) {

        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id) {

        if (categoryService.getCategoryById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}