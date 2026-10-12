package com.target.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.target.entities.Estoque;
import com.target.entities.Venda;
import com.target.entities.Vendedor;
import com.target.entities.response.enums.StatusPagamento;
import com.target.repositories.EstoqueRepository;
import com.target.repositories.VendaRepository;
import com.target.repositories.VendedorRepository;
import com.target.service.EstoqueService;
import com.target.service.StartDB;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StarDBImpl implements StartDB {

    private final EstoqueRepository estoqueRepository;
    private final VendaRepository vendaRepository;
    private final VendedorRepository vendedorRepository;
    private final EstoqueService estoqueService;

    @Override
    @Transactional
    public void initDB() {

        // Produtos
        Estoque teclado = criarProduto(
                1, "teclado", "39.99", 1000.0);

        Estoque lapis = criarProduto(
                2, "lapis", "1.50", 1000.0);

        Estoque caneta = criarProduto(
                3, "caneta", "2.50", 1000.0);

        Estoque pilha = criarProduto(
                4, "pilha", "5.00", 1000.0);

        Estoque mouse = criarProduto(
                5, "mouse", "25.00", 1000.0);

        estoqueRepository.saveAll(
                Arrays.asList(teclado, lapis, caneta, pilha, mouse));

        // Vendedores
     // Vendedores conforme o JSON do desafio
        Vendedor joao = criarVendedor("João Silva");
        Vendedor maria = criarVendedor("Maria Souza");
        Vendedor carlos = criarVendedor("Carlos Oliveira");
        Vendedor ana = criarVendedor("Ana Lima");

        vendedorRepository.saveAll(
                Arrays.asList(joao, maria, carlos, ana));

        // Vendas de teste: produto e quantidade vendida
        Venda venda = criarVenda(
        		joao, StatusPagamento.PAGO,
                new ItemTeste(teclado, 10),
                new ItemTeste(lapis, 300));

        venda.setValorJuros(BigDecimal.ZERO);
        Venda venda1 = criarVenda(
                maria, StatusPagamento.PAGO,
                new ItemTeste(pilha, 50),
                new ItemTeste(mouse, 10));
        venda1.setValorJuros(BigDecimal.ZERO);

Venda venda2 = criarVenda(
        carlos,
        StatusPagamento.PENDENTE,
        new ItemTeste(caneta, 1)
);

// Vencimento há 10 dias
venda2.setDataVencimento(LocalDate.now().minusDays(10));

// Simula pagamento hoje
venda2.setDataPagamento(LocalDate.now());

// Calcula os juros pelo atraso
BigDecimal juros = calcularJuros(
        venda2.getValorTotal(),
        venda2.getDataVencimento(),
        venda2.getDataPagamento()
);

// Persiste os juros na entidade
venda2.setValorJuros(
        juros.setScale(2, RoundingMode.HALF_UP)
);

// Mantém o valor original da venda separado dos juros
  venda2.getValorTotal().add(venda2.getValorJuros());
        Venda venda3 = criarVenda(
                ana, StatusPagamento.PAGO,
                new ItemTeste(pilha, 1));
        venda3.setValorJuros(BigDecimal.ZERO);

        Venda venda4 = criarVenda(
                ana, StatusPagamento.PAGO,
                new ItemTeste(teclado, 1),
                new ItemTeste(mouse, 1));
        venda4.setValorJuros(BigDecimal.ZERO);

        vendaRepository.saveAll(
                Arrays.asList(venda, venda1, venda2, venda3, venda4));
    }

    private BigDecimal calcularJuros(
            BigDecimal valor,
            LocalDate dataVencimento,
            LocalDate dataPagamento) {

        if (dataPagamento == null || dataVencimento == null) {
            return BigDecimal.ZERO;
        }

        if (!dataPagamento.isAfter(dataVencimento)) {
            return BigDecimal.ZERO;
        }

        long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(
                dataVencimento,
                dataPagamento
        );

        BigDecimal taxaDiaria = BigDecimal.valueOf(0.025);

        return valor
                .multiply(taxaDiaria)
                .multiply(BigDecimal.valueOf(diasAtraso));
    }
    
    private Estoque criarProduto(
            int codigo,
            String descricao,
            String preco,
            double quantidade) {

        return Estoque.builder()
                .codigoProduto(codigo)
                .descricaoProduto(descricao)
                .precoProduto(new BigDecimal(preco))
                .quantidade(quantidade)
                .build();
    }

    private Vendedor criarVendedor(String nome) {
        return Vendedor.builder()
                .nome(nome)
                .build();
    }

    private Venda criarVenda(
            Vendedor vendedor,
            StatusPagamento status,
            ItemTeste... itens) {

        Venda venda = Venda.builder()
                .instante(LocalDate.now())
                .dataVencimento(LocalDate.now())
                .vendedor(vendedor)
                .statusPagamento(status)
                .commission(BigDecimal.ZERO)
                .valorTotal(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (ItemTeste item : itens) {
            Estoque produto = item.produto();

            if (produto.getPrecoProduto() == null
                    || item.quantidade() <= 0) {
                throw new IllegalArgumentException(
                        "Produto e quantidade devem ser válidos");
            }

            total = total.add(
                    produto.getPrecoProduto()
                            .multiply(BigDecimal.valueOf(item.quantidade())));

            // Reduz somente a quantidade efetivamente vendida.
            estoqueService.reduceStock(
                    produto.getId(), item.quantidade());

            // Mantém o relacionamento de produtos da entidade Venda.
            venda.getProdutos().add(produto);
        }

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException(
                    "O valor total da venda deve ser maior que zero");
        }

        venda.setValorTotal(total);
        venda.setCommission(calculateCommission(total));

        if (status == StatusPagamento.PAGO) {
            venda.setDataPagamento(LocalDate.now());
        }

        System.out.println("Vendedor: " + vendedor.getNome()
                + " | Total: R$ " + total
                + " | Comissão: R$ " + venda.getCommission());

        return venda;
    }

    private BigDecimal calculateCommission(BigDecimal total) {

        if (total.compareTo(BigDecimal.valueOf(100)) < 0) {
            return BigDecimal.ZERO.setScale(2);
        }

        if (total.compareTo(BigDecimal.valueOf(500)) < 0) {
            return total.multiply(BigDecimal.valueOf(0.01))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return total.multiply(BigDecimal.valueOf(0.05))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private record ItemTeste(Estoque produto, int quantidade) {
    }
}
