package com.prp.blogs_be.services.impl;

import com.prp.blogs_be.domain.entities.Category;
import com.prp.blogs_be.repositories.CategoryRepository;
import com.prp.blogs_be.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {

		private final CategoryRepository categoryRepository;


		@Override
		public List<Category> listCategories() {
				return categoryRepository.findAllWithPostCount();
		}

		@Override
		@Transactional
		public Category createCategory(Category category) {
				if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
						throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
				}
				return categoryRepository.save(category);
		}

		@Override
		public void deleteCategory(UUID id) {
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent()) {
					if(!category.get().getPosts().isEmpty()) {
						throw new IllegalStateException("Category with id " + id + " already has posts");
					}
					categoryRepository.deleteById(id);
			}
		}

		@Override
		public Category getCategoryById(UUID id) {
				return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist"));
		}
}
