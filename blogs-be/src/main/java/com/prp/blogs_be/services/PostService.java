package com.prp.blogs_be.services;


import com.prp.blogs_be.domain.CreatePostRequest;
import com.prp.blogs_be.domain.UpdatePostRequest;
import com.prp.blogs_be.domain.dto.CreatePostRequestDto;
import com.prp.blogs_be.domain.entities.Post;
import com.prp.blogs_be.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
  
  List<Post> getAllPosts(UUID categoryId, UUID tagId);
  
  List<Post> getDraftPosts(User user);
  
  Post createPost(User user, CreatePostRequest createPostRequest);
  
  Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
  
  Post getPost(UUID id);
  
  void deletePost(UUID id);
}
