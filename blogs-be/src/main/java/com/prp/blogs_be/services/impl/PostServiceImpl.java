package com.prp.blogs_be.services.impl;

import com.prp.blogs_be.domain.CreatePostRequest;
import com.prp.blogs_be.domain.PostStatus;
import com.prp.blogs_be.domain.UpdatePostRequest;
import com.prp.blogs_be.domain.dto.CreatePostRequestDto;
import com.prp.blogs_be.domain.entities.Category;
import com.prp.blogs_be.domain.entities.Post;
import com.prp.blogs_be.domain.entities.Tag;
import com.prp.blogs_be.domain.entities.User;
import com.prp.blogs_be.repositories.PostRepository;
import com.prp.blogs_be.services.CategoryService;
import com.prp.blogs_be.services.PostService;
import com.prp.blogs_be.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl
		implements PostService {
  
  private static final int WORDS_PER_MINUTE = 60;
  private final PostRepository postRepository;
  private final TagService tagService;
  private final CategoryService categoryService;
  
  @Override
  @Transactional(readOnly = true)
  public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
	if (categoryId != null && tagId != null) {
	  Category category = categoryService.getCategoryById(categoryId);
	  Tag tag = tagService.getTagById(tagId);
	  return postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag);
	}
	
	if (categoryId != null) {
	  Category category = categoryService.getCategoryById(categoryId);
	  return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
	}
	
	if (tagId != null) {
	  Tag tag = tagService.getTagById(tagId);
	  return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
	}
	
	return postRepository.findAllByStatus(PostStatus.PUBLISHED);
  }
  
  @Override
  public List<Post> getDraftPosts(User user) {
	return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
  }
  
  @Transactional
  @Override
  public Post createPost(User user, CreatePostRequest createPostRequest) {
	Post post = new Post();
	post.setAuthor(user);
	post.setTitle(createPostRequest.getTitle());
	post.setContent(createPostRequest.getContent());
	post.setStatus(createPostRequest.getStatus());
	post.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
	
	Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
	post.setCategory(category);
	
	Set<UUID> tagIds = createPostRequest.getTagIds();
	List<Tag> tags = tagService.getTagsByIds(tagIds);
	post.setTags(new HashSet<>(tags));
	
	return postRepository.save(post);
  }
  
  @Override
  @Transactional
  public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
	Post post = postRepository.findById(id)
							  .orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
	post.setTitle(updatePostRequest.getTitle());
	post.setContent(updatePostRequest.getContent());
	post.setStatus(updatePostRequest.getStatus());
	post.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));
	
	UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
	if(!post.getCategory().getId().equals(updatePostRequestCategoryId)) {
	  Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
	  post.setCategory(newCategory);
	}
	
	Set<UUID> existingTagIds = post.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
	Set<UUID> tagIds = updatePostRequest.getTagIds();
	
	if(!existingTagIds.containsAll(tagIds)) {
	  List<Tag> newTags =  tagService.getTagsByIds(tagIds);
	  post.setTags(new HashSet<>(newTags));
	}
	
	return postRepository.save(post);
  }
  
  @Override
  public Post getPost(UUID id) {
	return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));
  }
  
  @Override
  public void deletePost(UUID id) {
	Post post = getPost(id);
	postRepository.delete(post);
  }
  
  
  private Integer calculateReadingTime(String content) {
	if (content == null || content.isEmpty()) {
	  return 0;
	}
	int wordCount = content.trim()
						   .split("\\s+").length;
	return (int) (Math.ceil((double) wordCount / WORDS_PER_MINUTE));
  }
}
