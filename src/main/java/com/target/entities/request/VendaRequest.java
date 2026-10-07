package com.target.entities.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendaRequest {

    @NotNull(message = "A data da venda é obrigatória")
    private LocalDate instante;

    @NotNull(message = "O vendedor é obrigatório")
    private UUID vendedor;

    @NotNull(message = "A data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @Valid
    @NotEmpty(message = "A venda deve possuir pelo menos um produto")
    @Builder.Default
    private List<ItemVendaRequest> produtos = new ArrayList<>();
}

