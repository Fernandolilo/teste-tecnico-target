package com.target.service;

import com.target.entities.Venda;
import com.target.entities.request.VendaRequest;

public interface VendaService {

	public Venda crate(VendaRequest request);
}
