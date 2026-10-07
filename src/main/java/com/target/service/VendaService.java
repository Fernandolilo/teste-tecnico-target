package com.target.service;

import java.math.BigDecimal;
import java.util.UUID;

import com.target.entities.Venda;
import com.target.entities.request.PagamentoRequest;
import com.target.entities.request.VendaRequest;
import com.target.entities.response.ComissoesListResponse;
import com.target.entities.response.VendaResponse;
import com.target.entities.response.VendasListResponse;

public interface VendaService {

	public Venda crate(VendaRequest request);
	
	VendaResponse findByVendaID(UUID id);
	
	VendasListResponse findAll();
	
	BigDecimal calculateCommission(BigDecimal valorTotal);
	
	ComissoesListResponse findAllCommissions();
	
	VendaResponse pay(UUID id, PagamentoRequest request);
}
