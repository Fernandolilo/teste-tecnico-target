package com.target.entities.request;

import java.time.LocalDate;

import com.target.entities.response.enums.StatusPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PagamentoRequest {

    private LocalDate dataPagamento;

    private StatusPagamento statusPagamento;
}