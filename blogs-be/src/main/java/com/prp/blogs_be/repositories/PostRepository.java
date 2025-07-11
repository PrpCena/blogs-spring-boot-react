package com.prp.blogs_be.repositories;

import com.prp.blogs_be.domain.PostStatus;
import com.prp.blogs_be.domain.entities.Category;
import com.prp.blogs_be.domain.entities.Post;
import com.prp.blogs_be.domain.entities.Tag;
import com.prp.blogs_be.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

		List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tags);
		List<Post> findAllByStatusAndCategory(PostStatus postStatus, Category category);
		List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus, Tag tags);
		List<Post> findAllByStatus(PostStatus status);
		List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
 }
