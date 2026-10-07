package com.target.entities.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JurosResponse {

    private BigDecimal valor;

    private LocalDate dataVencimento;

    private LocalDate dataAtual;

    private long diasAtraso;

    private BigDecimal percentual;

    private BigDecimal juros;

    private BigDecimal valorTotal;
}