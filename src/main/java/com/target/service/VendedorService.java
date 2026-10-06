package com.target.service;

import java.util.UUID;

import com.target.entities.Vendedor;
import com.target.entities.request.VendedorRequest;
import com.target.entities.response.VendedorResponse;

public interface VendedorService {

	VendedorResponse create (VendedorRequest request);
	
	VendedorResponse findById(UUID id);
	
	Vendedor findEntityById(UUID id);
}
