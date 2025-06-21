package com.prp.blogs_be.domain.entities;


import com.prp.blogs_be.domain.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {

		@Id
		@GeneratedValue(strategy = GenerationType.UUID)
		private UUID id;

		@Column(nullable = false)
		private String title;

		@Column(nullable = false, columnDefinition = "TEXT")
		private String content;

		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		private PostStatus status;

		@Column(nullable = false)
		private Integer readingTime;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "auhtor_id")
		private User author;

		@Column(nullable = false)
		private LocalDateTime createdAt;

		@Column(nullable = false)
		private LocalDateTime updatedAt;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "category_id", nullable = false)
		private Category category;

		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(
						name = "post_tags",
						joinColumns = @JoinColumn(name = "post_id"),
						inverseJoinColumns = @JoinColumn(name = "tag_id")
		)
		private Set<Tag> tags = new HashSet<>();

		@Override
		public boolean equals(Object o) {
				if (o == null || getClass() != o.getClass()) return false;
				Post post = (Post) o;
				return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content) && status == post.status && Objects.equals(readingTime, post.readingTime) && Objects.equals(createdAt, post.createdAt) && Objects.equals(updatedAt, post.updatedAt);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, title, content, status, readingTime, createdAt, updatedAt);
		}

		@PrePersist
		public void prePersist() {
				LocalDateTime now = LocalDateTime.now();
				this.createdAt = now;
				this.updatedAt = now;
		}

		@PreUpdate
		public void preUpdate() {
				this.updatedAt = LocalDateTime.now();
		}
}

