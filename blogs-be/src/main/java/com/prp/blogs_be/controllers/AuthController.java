package com.prp.blogs_be.controllers;

import com.prp.blogs_be.domain.dto.AuthResponse;
import com.prp.blogs_be.domain.dto.LoginRequest;
import com.prp.blogs_be.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/login")
@AllArgsConstructor
public class AuthController {
  
  private final AuthenticationService authenticationService;
  
  
  @PostMapping
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
	UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
	String tokenValue = authenticationService.generateToken(userDetails);
	AuthResponse authResponse = AuthResponse.builder()
											.token(tokenValue)
											.expiresIn(86400)
											.build();
	return ResponseEntity.ok(authResponse);
  }
  
  
}
