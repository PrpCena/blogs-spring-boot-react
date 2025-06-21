package com.prp.blogs_be.mappers;

import com.prp.blogs_be.domain.CreatePostRequest;
import com.prp.blogs_be.domain.UpdatePostRequest;
import com.prp.blogs_be.domain.dto.CreatePostRequestDto;
import com.prp.blogs_be.domain.dto.PostDto;
import com.prp.blogs_be.domain.dto.UpdatePostRequestDto;
import com.prp.blogs_be.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
		@Mapping(target = "author", source = "author")
		@Mapping(target = "category", source = "category")
		@Mapping(target = "tags", source = "tags")
		PostDto toDto(Post post);
		
		CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
		
		UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
