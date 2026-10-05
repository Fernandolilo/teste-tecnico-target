package com.target.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Vendedor;
import com.target.entities.request.VendedorRequest;
import com.target.entities.response.VendedorResponse;
import com.target.repositories.VendedorRepository;
import com.target.service.VendedorService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class VendedorServiceImpl implements VendedorService{
	
	private final ModelMapper mapper;
	private final VendedorRepository repository;

	@Override
	public VendedorResponse create(VendedorRequest request) {
		
		
		//mapeando entidade e request
		Vendedor entity = mapper.map(request, Vendedor.class);
		
		//salvando
		repository.save(entity);
		
		//retornando		
		return mapper.map(entity, VendedorResponse.class);
	}

}
