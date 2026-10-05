package com.target.service;

import com.target.entities.request.EstoqueRequest;
import com.target.entities.response.EstoqueResponse;

public interface EstoqueService {

	EstoqueResponse create (EstoqueRequest request);
}
