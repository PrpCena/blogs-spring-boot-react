package com.prp.blogs_be.services;

import com.prp.blogs_be.domain.entities.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
		List<Tag> getTags();
		List<Tag> createTags(Set<String> tagNames);
		void deleteTags(UUID id);
		Tag getTagById(UUID id);
		List<Tag> getTagsByIds(Set<UUID> ids);
}
