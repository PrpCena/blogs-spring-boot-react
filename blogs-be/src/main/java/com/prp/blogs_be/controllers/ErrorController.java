package com.prp.blogs_be.controllers;


import com.prp.blogs_be.domain.dto.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

		@ExceptionHandler(Exception.class)
		public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
				log.error("Caught exception", e);
				ApiErrorResponse error =  ApiErrorResponse.builder()
								.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
								.message("An Unexpected error occurred")
								.build();
				return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@ExceptionHandler(IllegalArgumentException.class)
		public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
				log.error("Caught illegal argument exception", e);
				ApiErrorResponse error = ApiErrorResponse.builder()
								.status(HttpStatus.BAD_REQUEST.value())
								.message("Illegal argument")
								.build();
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}


		@ExceptionHandler(IllegalStateException.class)
		public ResponseEntity<ApiErrorResponse> handleIllegalState(IllegalStateException e) {
				ApiErrorResponse error = ApiErrorResponse.builder()
								.status(HttpStatus.CONFLICT.value())
								.message("Illegal argument")
								.build();
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		@ExceptionHandler(BadCredentialsException.class)
		public ResponseEntity<ApiErrorResponse> handleIllegalState(BadCredentialsException e) {
				ApiErrorResponse error = ApiErrorResponse.builder()
								.status(HttpStatus.UNAUTHORIZED.value())
								.message("Incorrect username or password")
								.build();
				return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}

		@ExceptionHandler(EntityNotFoundException.class)
		public ResponseEntity<ApiErrorResponse> handleIllegalState(EntityNotFoundException e) {
				ApiErrorResponse error = ApiErrorResponse.builder()
								.status(HttpStatus.NOT_FOUND.value())
								.message(e.getMessage())
								.build();
				return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}


}
