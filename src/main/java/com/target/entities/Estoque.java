package com.target.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	private double quantidade;
	
	@JsonBackReference
	@ManyToMany(mappedBy = "produtos")
	@Builder.Default
	@ToString.Exclude
	private List<Venda> vendas = new ArrayList<>();
	
	
	
}
