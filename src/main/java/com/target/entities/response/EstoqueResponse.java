package com.target.entities.response;

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
public class EstoqueResponse {

	@Column(name = "CODIGO_PRODUTO")
	private int codigoProduto;
	
	@Column(name = "DESCRICAO_PRODUTO")
	private String descricaoProduto;
	
	@Column(name = "PRECO_PRODUTO")
	private BigDecimal precoProduto;
	
	@Column(name = "ESTOQUE_PRODUTO")
	private double estoque;

}
