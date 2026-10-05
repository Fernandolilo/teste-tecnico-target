package com.target.service;

import com.target.entities.request.VendedorRequest;
import com.target.entities.response.VendedorResponse;

public interface VendedorService {

	VendedorResponse create (VendedorRequest request);
}
