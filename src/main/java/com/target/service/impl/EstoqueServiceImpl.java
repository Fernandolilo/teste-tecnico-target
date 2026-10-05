package com.target.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Estoque;
import com.target.entities.request.EstoqueRequest;
import com.target.entities.response.EstoqueResponse;
import com.target.repositories.EstoqueRepository;
import com.target.service.EstoqueService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService{
	
	private final ModelMapper mapper;
	private final EstoqueRepository repository;

	@Override
	public EstoqueResponse create(EstoqueRequest request) {
		
		// 1. Mapeia o request para a entidade
	    Estoque entity = mapper.map(request, Estoque.class);
	    
	    // 2. Busca o maior código atual no banco
	    Integer maiorCodigo = repository.encontrarMaiorCodigoProduto();
	    
	    // 3. Define o próximo código (se vazio, começa com 1, senão incrementa +1)
	    int proximoCodigo = (maiorCodigo == null) ? 1 : maiorCodigo + 1;
	    entity.setCodigoProduto(proximoCodigo);
	    
	    // 4. Salva a entidade no banco de dados
	    Estoque savedEntity = repository.save(entity);
	    
	    // 5. Mapeia a entidade salva (já com ID e código atualizados) para o Response
	    EstoqueResponse response = mapper.map(savedEntity, EstoqueResponse.class);
	    
	    // 6. Retorna o Response conforme solicitado
	    return response;
	}

}
