package com.prp.blogs_be.security;

import com.prp.blogs_be.domain.entities.User;
import com.prp.blogs_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

		private final UserRepository userRepository;

		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
				return new BlogUserDetails(user);
		}
}
