package com.prp.blogs_be.mappers;


import com.prp.blogs_be.domain.PostStatus;
import com.prp.blogs_be.domain.dto.TagDto;
import com.prp.blogs_be.domain.entities.Post;
import com.prp.blogs_be.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
  
  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostcount")
  TagDto toTagDto(Tag tag);
  
  @Named("calculatePostcount")
  default Integer calculatePostcount(Set<Post> posts) {
	if (posts == null || posts.isEmpty()) {
	  return 0;
	}
	
	return (int) posts.stream()
					  .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
					  .count();
  }
  
}
