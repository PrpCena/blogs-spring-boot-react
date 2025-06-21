package com.prp.blogs_be.controllers;


import com.prp.blogs_be.domain.CreatePostRequest;
import com.prp.blogs_be.domain.UpdatePostRequest;
import com.prp.blogs_be.domain.dto.CreatePostRequestDto;
import com.prp.blogs_be.domain.dto.PostDto;
import com.prp.blogs_be.domain.dto.UpdatePostRequestDto;
import com.prp.blogs_be.domain.entities.Post;
import com.prp.blogs_be.domain.entities.User;
import com.prp.blogs_be.mappers.PostMapper;
import com.prp.blogs_be.services.PostService;
import com.prp.blogs_be.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
  
  private final PostService postService;
  private final PostMapper postMapper;
  private final UserService userService;
  
  
  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts(
		  @RequestParam(required = false) UUID categoryId,
		  @RequestParam(required = false) UUID tagId
  ) {
	List<Post> posts = postService.getAllPosts(categoryId, tagId);
	List<PostDto> postDtos = posts.stream()
								  .map(postMapper::toDto)
								  .toList();
	
	return ResponseEntity.ok(postDtos);
  }
  
  @GetMapping(path = "/drafts")
  public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
	User loggedInUser = userService.getUserById(userId);
	List<Post> posts = postService.getDraftPosts(loggedInUser);
	List<PostDto> postDtos = posts.stream()
								  .map(postMapper::toDto)
								  .toList();
	return ResponseEntity.ok(postDtos);
  }
  
  @PostMapping
  public ResponseEntity<PostDto> createPost(
		  @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
		  @RequestAttribute UUID userId
  ) {
	User loggedInUser = userService.getUserById(userId);
	CreatePostRequest toCreatePostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
	Post createdPost = postService.createPost(loggedInUser, toCreatePostRequest);
	PostDto postDto = postMapper.toDto(createdPost);
	return new ResponseEntity<>(postDto, HttpStatus.CREATED);
  }
  
  
  @PutMapping(path = "/{id}")
  public ResponseEntity<PostDto> updatePost(
		  @PathVariable UUID id,
		  @Valid  @RequestBody UpdatePostRequestDto updatePostRequestDto
  ) {
	UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
	Post updatedPost = postService.updatePost(id, updatePostRequest);
	PostDto postDto = postMapper.toDto(updatedPost);
	return ResponseEntity.ok(postDto);
  }
  
  @GetMapping(path = "/{id}")
  public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
	Post post = postService.getPost(id);
	PostDto postDto = postMapper.toDto(post);
	return ResponseEntity.ok(postDto);
  }
  
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
	postService.deletePost(id);
	return ResponseEntity.noContent().build();
  }
}
