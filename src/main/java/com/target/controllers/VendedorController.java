package com.target.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.entities.request.VendedorRequest;
import com.target.entities.response.VendedorResponse;
import com.target.service.VendedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendedor")
@RequiredArgsConstructor
public class VendedorController {
	
	private final VendedorService service;

	@PostMapping
	public ResponseEntity<VendedorResponse> create (@Valid @RequestBody VendedorRequest request){
		VendedorResponse vendedor = service.create(request);
		 return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(vendedor);
	}
}
