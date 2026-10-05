package com.target.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // <-- Ajustado para OneToMany
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
public class Vendedor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "NOME_VENDEDOR")
	private String nome;
	
	// Corrigido para OneToMany, mapeando pelo campo "vendedor" lá da classe Venda
	@OneToMany(mappedBy = "vendedor")
	@Builder.Default
	@ToString.Exclude
	private List<Venda> vendas = new ArrayList<>();
}