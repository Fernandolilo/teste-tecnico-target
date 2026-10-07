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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "O código do produto é obrigatório")
    @Min(value = 1, message = "O código do produto deve ser maior que zero")
    @Column(name = "CODIGO_PRODUTO", nullable = false)
    private Integer codigoProduto;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Column(
        name = "DESCRICAO_PRODUTO",
        nullable = false
    )
    private String descricaoProduto;

    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(
        value = "0.01",
        message = "O preço do produto deve ser maior que zero"
    )
    @Column(
        name = "PRECO_PRODUTO",
        nullable = false
    )
    private BigDecimal precoProduto;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @DecimalMin(
        value = "0.0",
        inclusive = true,
        message = "A quantidade não pode ser negativa"
    )
    @Column(
        name = "ESTOQUE_PRODUTO",
        nullable = false
    )
    private Double quantidade;

    @JsonBackReference
    @ManyToMany(mappedBy = "produtos")
    @Builder.Default
    @ToString.Exclude
    private List<Venda> vendas = new ArrayList<>();
}