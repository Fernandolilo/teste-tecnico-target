package com.target.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Estoque;
import com.target.entities.Venda;
import com.target.entities.request.VendaRequest;
import com.target.repositories.EstoqueRepository;
import com.target.repositories.VendaRepository;
import com.target.service.VendaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaServiceImpl implements VendaService {

	private final VendaRepository repository;
	private final ModelMapper mapper;
	private final EstoqueRepository estoqueRepository;

	@Override
	public Venda crate(VendaRequest request) {

		Venda venda = mapper.map(request, Venda.class);

		venda.getProdutos().forEach(item -> {

			Estoque estoqueResponse = estoqueRepository.findById(item.getId())
					.orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque"));

			if (estoqueResponse.getQuantidade() < item.getQuantidade()) {
				throw new RuntimeException(
						"Estoque insuficiente para o produto: " + estoqueResponse.getDescricaoProduto());
			}

			estoqueResponse.setQuantidade(estoqueResponse.getQuantidade() - item.getQuantidade());

			estoqueRepository.save(estoqueResponse);
		});

		return repository.save(venda);
	}
}
