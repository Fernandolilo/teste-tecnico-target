package com.target.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.entities.request.EstoqueRequest;
import com.target.entities.response.EstoqueListResponse;
import com.target.entities.response.EstoqueResponse;
import com.target.service.EstoqueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {
	
	private final EstoqueService service;

	@PostMapping
	public ResponseEntity<EstoqueResponse> create (@Valid @RequestBody EstoqueRequest request){
		EstoqueResponse estoque = service.create(request);
		 return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(estoque);
	}
	
	@GetMapping
	public ResponseEntity<EstoqueListResponse> findAll() {
	    return ResponseEntity.ok(service.findAll());
	}
}
