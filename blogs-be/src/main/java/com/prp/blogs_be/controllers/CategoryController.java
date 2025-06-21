package com.prp.blogs_be.controllers;

import com.prp.blogs_be.domain.dto.CategoryDto;
import com.prp.blogs_be.domain.dto.CreateCategoryRequest;
import com.prp.blogs_be.domain.entities.Category;
import com.prp.blogs_be.mappers.CategoryMapper;
import com.prp.blogs_be.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
  
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;
  
  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {
	List<CategoryDto> categories = categoryService.listCategories()
												  .stream()
												  .map(categoryMapper::toDto)
												  .toList();
	return ResponseEntity.ok(categories);
  }
  
  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
	Category category = categoryMapper.toEntity(createCategoryRequest);
	Category savedCategory = categoryService.createCategory(category);
	return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
  }
  
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<CategoryDto> deleteCategory(@PathVariable UUID id) {
	categoryService.deleteCategory(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
}

