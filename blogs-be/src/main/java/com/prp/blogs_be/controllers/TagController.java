package com.prp.blogs_be.controllers;

import com.prp.blogs_be.domain.dto.CreateTagsRequest;
import com.prp.blogs_be.domain.dto.TagDto;
import com.prp.blogs_be.mappers.TagMapper;
import com.prp.blogs_be.services.TagService;
import lombok.RequiredArgsConstructor;
import com.prp.blogs_be.domain.entities.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
  
  private final TagService tagService;
  private final TagMapper tagMapper;
  
  @GetMapping
  public ResponseEntity<List<TagDto>> getAllTags() {
	List<Tag> tags = tagService.getTags();
	
	List<TagDto> tagDtos = tags.stream()
							   .map(tagMapper::toTagDto)
							   .toList();
	
	return ResponseEntity.ok(tagDtos);
  }
  
  @PostMapping
  public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
	List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
	List<TagDto> createdTags = savedTags.stream()
										.map(tagMapper::toTagDto)
										.toList();
	return new ResponseEntity<>(createdTags, HttpStatus.CREATED);
  }
  
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteTags(@PathVariable UUID id) {
	tagService.deleteTags(id);
	return ResponseEntity.noContent()
						 .build();
  }
}
