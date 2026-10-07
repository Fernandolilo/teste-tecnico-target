package com.target.entities.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EstoqueRequest {

    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricaoProduto;

    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(
        value = "0.01",
        message = "O preço do produto deve ser maior que zero"
    )
    private BigDecimal precoProduto;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
    private Double quantidade;
}
