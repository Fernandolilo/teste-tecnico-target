package com.target.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.target.entities.request.EstoqueRequest;
import com.target.entities.response.EstoqueListResponse;
import com.target.entities.response.EstoqueResponse;

public interface EstoqueService {

	EstoqueResponse create (EstoqueRequest request);
	
	EstoqueResponse findById(UUID id);
	
	void reduceStock (UUID id, double quantidade);
	
	 BigDecimal calculateItemTotal(UUID produtoId, Integer quantidade);
	 
	 EstoqueListResponse findAll();
}
