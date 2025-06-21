package com.prp.blogs_be.services;


import com.prp.blogs_be.domain.entities.User;

import java.util.UUID;

public interface UserService {
	User getUserById(UUID Id);
}
