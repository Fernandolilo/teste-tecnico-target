package com.target.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Vendedor;
import com.target.entities.request.VendedorRequest;
import com.target.entities.response.VendedorResponse;
import com.target.repositories.VendedorRepository;
import com.target.service.VendedorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class VendedorServiceImpl implements VendedorService{
	
	private final ModelMapper mapper;
	private final VendedorRepository repository;

	@Transactional
	@Override
	public VendedorResponse create(VendedorRequest request) {
		
		
		//mapeando entidade e request
		Vendedor entity = mapper.map(request, Vendedor.class);
		
		//salvando
		repository.save(entity);
		
		//retornando		
		return mapper.map(entity, VendedorResponse.class);
	}

	@Override
	public VendedorResponse findById(UUID id) {

	    Optional<Vendedor> entity = repository.findById(id);

	    if (entity.isEmpty()) {
	        throw new RuntimeException("Vendedor não encontrado");
	    }

	    return mapper.map(entity.get(), VendedorResponse.class);
	}

	 @Override
	    public Vendedor findEntityById(UUID id) {

	        return repository.findById(id)
	                .orElseThrow(() ->
	                    new RuntimeException("Vendedor não encontrado"));
	    }

}
