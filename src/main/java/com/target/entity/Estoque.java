package com.target.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)	
	private UUID id;
	
	@Column(name = "CODIGO_PRODUTO")
	private int codigoProduto;
	
	@Column(name = "DESCRICAO_PRODUTO")
	private String descricaoProduto;
	
	@Column(name = "PRECO_PRODUTO")
	private BigDecimal precoProduto;
	
	@Column(name = "ESTOQUE_PRODUTO")
	private double estoque;
	
}
