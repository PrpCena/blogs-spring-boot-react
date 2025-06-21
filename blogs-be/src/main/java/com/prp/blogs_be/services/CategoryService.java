package com.prp.blogs_be.services;


import com.prp.blogs_be.domain.entities.Category;

import java.util.List;
import java.util.UUID;


public interface CategoryService {
		List<Category> listCategories();
		Category createCategory(Category category);
		void deleteCategory(UUID id);
		Category getCategoryById(UUID id);
}
