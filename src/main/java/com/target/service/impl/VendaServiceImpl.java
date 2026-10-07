package com.target.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Venda;
import com.target.entities.Vendedor;
import com.target.entities.request.VendaRequest;
import com.target.entities.response.VendaListResponse;
import com.target.entities.response.VendaResponse;
import com.target.entities.response.VendasListResponse;
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

	        Vendedor vendedor =
	                vendedorService.findEntityById(request.getVendedor());

	        venda.setVendedor(vendedor);

	        venda.getProdutos().forEach(item -> {
	            estoqueService.reduceStock(
	                    item.getId(),
	                    item.getQuantidade()
	            );
	        });

	        BigDecimal total = calculateTotal(request);

	        venda.setValorTotal(total);

	        return repository.save(venda);
	    }

	   
	    public BigDecimal calculateTotal(VendaRequest request) {

	        return request.getProdutos()
	                .stream()
	                .map(item -> estoqueService.calculateItemTotal(
	                        item.getProdutoId(),
	                        item.getQuantidade()
	                ))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	    }

	    @Override
	    public VendaResponse findByVendaID(UUID id) {

	        Venda entity = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Venda não encontrada"));

	        return mapper.map(entity, VendaResponse.class);
	    }

	    @Override
	    public VendasListResponse findAll() {

	    	  List<VendaListResponse> vendas = repository.findAll()
	    	            .stream()
	    	            .map(venda -> VendaListResponse.builder()
	    	                    .valor(venda.getValorTotal())
	    	                    .vendedor(venda.getVendedor().getNome())
	    	                    .build())
	    	            .toList();

	        return VendasListResponse.builder()
	                .vendas(vendas)
	                .build();
	    }

		@Override
		public BigDecimal comicao(BigDecimal valorTotal) {
			// TODO Auto-generated method stub
			return null;
		}
}

	


