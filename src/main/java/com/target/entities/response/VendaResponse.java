package com.target.entities.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.target.entities.Estoque;
import com.target.entities.Vendedor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendaResponse {

	private LocalDate instante;
	private BigDecimal valorTotal;
	private Vendedor vendedor;

	@Builder.Default
	private List<Estoque> produtos = new ArrayList<>();
}
