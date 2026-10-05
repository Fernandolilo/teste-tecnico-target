package com.target.entities.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendedorRequest {

	@Column(name = "NOME_VENDEDOR")
	private String nome;
}
