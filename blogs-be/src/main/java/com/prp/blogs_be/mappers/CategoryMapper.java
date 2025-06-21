package com.prp.blogs_be.mappers;

import com.prp.blogs_be.domain.PostStatus;
import com.prp.blogs_be.domain.dto.CategoryDto;
import com.prp.blogs_be.domain.dto.CreateCategoryRequest;
import com.prp.blogs_be.domain.entities.Category;
import com.prp.blogs_be.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
		@Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
		CategoryDto toDto(Category category);

		Category toEntity(CreateCategoryRequest createCategoryRequest);

		@Named("calculatePostCount")
		default long calculatePostCount(List<Post> posts) {
				if (posts == null || posts.isEmpty()) {
						return 0;
				}
				return posts.stream()
								.filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
								.count();
		}
}
