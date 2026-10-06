package com.target.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Venda;
import com.target.entities.Vendedor;
import com.target.entities.request.VendaRequest;
import com.target.entities.response.VendedorResponse;
import com.target.repositories.VendaRepository;
import com.target.service.EstoqueService;
import com.target.service.VendaService;
import com.target.service.VendedorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaServiceImpl implements VendaService {

	private final VendaRepository repository;
	private final ModelMapper mapper;
	private final EstoqueService estoqueService;
	private final VendedorService  vendedorService;
	
	
	@Transactional
	@Override
	public Venda crate(VendaRequest request) {
		Venda venda = mapper.map(request, Venda.class);
		
		  Vendedor vendedor = vendedorService.findEntityById(request.getVendedor());
	   
		venda.getProdutos().forEach(item -> {

			estoqueService.reduceStock(item.getId(), item.getQuantidade());

		});
		
		   venda.setVendedor(vendedor);


		return repository.save(venda);
	}


	

}
