package com.target.entities.request;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EstoqueRequest {
	

	@Column(name = "DESCRICAO_PRODUTO")
	private String descricaoProduto;
	
	@Column(name = "PRECO_PRODUTO")
	private BigDecimal precoProduto;
	
	@Column(name = "ESTOQUE_PRODUTO")
	private double quantidade;

}
