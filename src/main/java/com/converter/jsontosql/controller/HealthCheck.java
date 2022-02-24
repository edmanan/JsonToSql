package com.converter.jsontosql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface HealthCheck {

	
	@GetMapping
	ResponseEntity<String> healthCheck();

}