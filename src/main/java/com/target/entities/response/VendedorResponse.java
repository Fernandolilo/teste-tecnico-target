package com.target.entities.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendedorResponse {

	@Column(name = "NOME_VENDEDOR")
	private String nome;
}
