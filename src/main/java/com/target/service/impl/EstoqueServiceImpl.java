package com.target.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Estoque;
import com.target.entities.request.EstoqueRequest;
import com.target.entities.response.EstoqueListResponse;
import com.target.entities.response.EstoqueResponse;
import com.target.exceptions.BusinessException;
import com.target.exceptions.ResourceNotFoundException;
import com.target.repositories.EstoqueRepository;
import com.target.service.EstoqueService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

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

	@Override
	public EstoqueResponse findById(UUID id) {

		Optional<Estoque> entity = repository.findById(id);

		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("Produto não encontrado");
		}
		EstoqueResponse response = mapper.map(entity, EstoqueResponse.class);

		return response;
	}

	@Transactional
	@Override
	public void reduceStock(UUID id, double quantidade) {

		Estoque estoque = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

		if (estoque.getQuantidade() < quantidade) {
			throw new BusinessException("Estoque insuficiente para o produto: " + estoque.getDescricaoProduto());
		}

		estoque.setQuantidade(estoque.getQuantidade() - quantidade);

		repository.save(estoque);
	}

	
	@Override
	public BigDecimal calculateItemTotal(UUID produtoId, Integer quantidade) {
		Estoque estoque = repository.findById(produtoId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Produto não encontrado"));

	    return estoque.getPrecoProduto()
	            .multiply(BigDecimal.valueOf(quantidade));	}

	@Override
	public EstoqueListResponse  findAll() {
	
		   List<EstoqueResponse> response = repository.findAll()
		            .stream()
		            .map(entity -> mapper.map(entity, EstoqueResponse.class))
		            .toList();

		    return EstoqueListResponse.builder()
		            .estoque(response)
		            .build();
	}

}
