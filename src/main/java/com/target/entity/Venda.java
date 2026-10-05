package com.target.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Venda {

	private UUID id;
	private LocalDate instante;
	
	private Vendedor vendedor;
	
	private List<Estoque> produtos = new ArrayList();
}
