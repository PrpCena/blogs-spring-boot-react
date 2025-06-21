package com.prp.blogs_be.services.impl;


import com.prp.blogs_be.domain.entities.User;
import com.prp.blogs_be.repositories.UserRepository;
import com.prp.blogs_be.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
		implements UserService {
  
  
  private final UserRepository userRepository;
  
  @Override
  public User getUserById(UUID Id) {
	return userRepository.findById(Id)
						 .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }
}
