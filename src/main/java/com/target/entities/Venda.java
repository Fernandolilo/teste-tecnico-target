package com.target.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.target.entities.response.enums.StatusPagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
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
@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "A data da venda é obrigatória")
    @Column(name = "DATA_VENDA", nullable = false)
    private LocalDate instante;

    @NotNull(message = "O valor total da venda é obrigatório")
    @DecimalMin(
        value = "0.01",
        message = "O valor total deve ser maior que zero"
    )
    private BigDecimal valorTotal;

    @NotNull(message = "A comissão é obrigatória")
    @DecimalMin(
        value = "0.00",
        message = "A comissão não pode ser negativa"
    )
    private BigDecimal commission;

    //@NotNull(message = "O valor dos juros é obrigatório")
    @DecimalMin(
        value = "0.00",
        message = "O valor dos juros não pode ser negativo"
    )
    private BigDecimal valorJuros;

    @NotNull(message = "A data de vencimento é obrigatória")
    @Column(name = "DATA_VENCIMENTO", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @NotNull(message = "O status do pagamento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "STATUS_PAGAMENTO",
        nullable = false
    )
    @Builder.Default
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @NotNull(message = "O vendedor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @NotEmpty(message = "A venda deve possuir pelo menos um produto")
    @ManyToMany
    @JoinTable(
        name = "venda_produto",
        joinColumns = @JoinColumn(name = "venda_id"),
        inverseJoinColumns = @JoinColumn(name = "estoque_id")
    )
    @Builder.Default
    private List<Estoque> produtos = new ArrayList<>();
}