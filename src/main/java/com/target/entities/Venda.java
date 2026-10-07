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

	@Column(name = "DATA_VENDA")
	private LocalDate instante;

	private BigDecimal valorTotal;

	private BigDecimal commission;
	
	private BigDecimal valorJuros;

	@Column(name = "DATA_VENCIMENTO")
	private LocalDate dataVencimento;
	
	@Column(name = "DATA_PAGAMENTO")
	private LocalDate dataPagamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_PAGAMENTO")
	@Builder.Default
	private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;

	@ManyToMany
	@JoinTable(name = "venda_produto", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "estoque_id"))
	@Builder.Default
	private List<Estoque> produtos = new ArrayList<>();

}
