package com.prp.blogs_be.services.impl;

import com.prp.blogs_be.domain.entities.Tag;
import com.prp.blogs_be.repositories.TagRepository;
import com.prp.blogs_be.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

		private final TagRepository tagRepository;

		@Override
		public List<Tag> getTags() {
				return tagRepository.findAllWithPostCount();
		}

		@Transactional
		@Override
		public List<Tag> createTags(Set<String> tagNames) {
				List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
				Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());
				List<Tag> newTags = tagNames.stream().filter(name -> !existingTagNames.contains(name)).map(name -> Tag.builder().name(name).posts(new HashSet<>()).build()).toList();

				List<Tag> savedTags = new ArrayList<>();
				if (!newTags.isEmpty()) {
						savedTags = tagRepository.saveAll(newTags);
				}

				return savedTags;
		}

		@Override
		public void deleteTags(UUID id) {
				tagRepository.findById(id).ifPresent(tag -> {
						if (!tag.getPosts().isEmpty()) {
								throw new IllegalStateException("Cannot delete tags because tag posts are not empty");
						}
						tagRepository.deleteById(id);
				});
		}

		@Override
		public Tag getTagById(UUID id) {
				return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find tag with id " + id));
		}
  
  @Override
  public List<Tag> getTagsByIds(Set<UUID> ids) {
	List<Tag> foundTags =  tagRepository.findAllById(ids);
	if(foundTags.size() != ids.size()) {
	  throw new EntityNotFoundException("Cannot find tags with ids " + ids);
	}
	return foundTags;
  }
  
}
