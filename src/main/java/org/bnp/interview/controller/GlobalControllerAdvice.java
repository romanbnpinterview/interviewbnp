package org.bnp.interview.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.bnp.interview.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
		logError(ex);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", OffsetDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("message", ex.getMessage());
		body.put("path", request.getRequestURI());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	private void logError(final Exception ex) {
		log.error("Failed to process the incoming request", ex);
	}
}
