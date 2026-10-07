package com.target.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.target.entities.Venda;
import com.target.entities.Vendedor;
import com.target.entities.request.PagamentoRequest;
import com.target.entities.request.VendaRequest;
import com.target.entities.response.ComissaoListResponse;
import com.target.entities.response.ComissoesListResponse;
import com.target.entities.response.VendaListResponse;
import com.target.entities.response.VendaResponse;
import com.target.entities.response.VendasListResponse;
import com.target.entities.response.enums.StatusPagamento;
import com.target.repositories.VendaRepository;
import com.target.service.EstoqueService;
import com.target.service.VendaService;
import com.target.service.VendedorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaServiceImpl implements VendaService {

	private final VendaRepository repository;
	private final ModelMapper mapper;
	private final EstoqueService estoqueService;
	private final VendedorService  vendedorService;
	
	
	    @Transactional
	    @Override
	    public Venda crate(VendaRequest request) {

	    	  Venda venda = mapper.map(request, Venda.class);

		        Vendedor vendedor =
		                vendedorService.findEntityById(request.getVendedor());

		        venda.setVendedor(vendedor);
		       
		        venda.getProdutos().forEach(item -> {
		            estoqueService.reduceStock(
		                    item.getId(),
		                    item.getQuantidade()
		            );
		        });
		        BigDecimal total = calculateTotal(request);

		        venda.setValorTotal(total);

		        BigDecimal commission = calculateCommission(total);

		        venda.setCommission(commission);

		        venda.setStatusPagamento(StatusPagamento.PENDENTE);

		        return repository.save(venda);
	    }

	   
	    public BigDecimal calculateTotal(VendaRequest request) {

	        return request.getProdutos()
	                .stream()
	                .map(item -> estoqueService.calculateItemTotal(
	                        item.getProdutoId(),
	                        item.getQuantidade()
	                ))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	    }

	    @Override
	    public VendaResponse findByVendaID(UUID id) {

	        Venda entity = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Venda não encontrada"));

	        return mapper.map(entity, VendaResponse.class);
	    }

	    @Override
	    public VendasListResponse findAll() {

	    	  List<VendaListResponse> vendas = repository.findAll()
	    	            .stream()
	    	            .map(venda -> VendaListResponse.builder()
	    	                    .valor(venda.getValorTotal())
	    	                    .vendedor(venda.getVendedor().getNome())
	    	                    .build())
	    	            .toList();

	        return VendasListResponse.builder()
	                .vendas(vendas)
	                .build();
	    }

	    
	    //CALCULO DE COMISSAO
		@Override
		public BigDecimal calculateCommission(BigDecimal valorTotal) {
			   if (valorTotal.compareTo(BigDecimal.valueOf(100)) < 0) {
			        return BigDecimal.ZERO;
			    }

			    if (valorTotal.compareTo(BigDecimal.valueOf(500)) < 0) {
			        return valorTotal.multiply(BigDecimal.valueOf(0.01));
			    }

			    return valorTotal.multiply(BigDecimal.valueOf(0.05));
		}

		
		/*
		 * METODO CRIADO PARA VER AS COMISSOES 
		 */

		@Override
		public ComissoesListResponse findAllCommissions() {

		    Map<String, BigDecimal> comissoes = repository.findAll()
		            .stream()
		            .collect(Collectors.groupingBy(
		                    venda -> venda.getVendedor().getNome(),
		                    Collectors.reducing(
		                            BigDecimal.ZERO,
		                            venda -> venda.getCommission() != null
		                                    ? venda.getCommission()
		                                    : BigDecimal.ZERO,
		                            BigDecimal::add
		                    )
		            ));

		    List<ComissaoListResponse> vendedores = comissoes.entrySet()
		            .stream()
		            .map(entry -> ComissaoListResponse.builder()
		                    .vendedor(entry.getKey())
		                    .comissao(entry.getValue())
		                    .build())
		            .toList();

		    return ComissoesListResponse.builder()
		            .vendedores(vendedores)
		            .build();
		}
		
		/*
		 * CRIEI O PAGAMENTO PARA TER MAIS SENTIDO NA QUESTÃO DE COBRACA DE JUROS. 
		 * 
		 */
		@Override
		@Transactional
		public VendaResponse pay(UUID id, PagamentoRequest request) {

		    Venda venda = repository.findById(id)
		            .orElseThrow(() ->
		                    new RuntimeException("Venda não encontrada"));

		    if (venda.getStatusPagamento() == StatusPagamento.PAGO) {
		        throw new RuntimeException("Venda já está paga");
		    }

		    BigDecimal juros = calculateInterest(
		            venda.getValorTotal(),
		            venda.getDataVencimento(),
		            request.getDataPagamento()
		    );

		    venda.setValorJuros(juros);

		    venda.setStatusPagamento(
		            request.getStatusPagamento()
		    );

		    venda.setDataPagamento(
		            request.getDataPagamento()
		    );

		    repository.save(venda);

		    return mapper.map(venda, VendaResponse.class);
		}
		
		
		
		/*
		 * ATUALIZA O PAGAMENTO JA CRIA A TAXA DE JUROS DE ACORDO COM O PAGAMENTO.  
		 */
		private BigDecimal calculateInterest(
		        BigDecimal valor,
		        LocalDate dataVencimento,
		        LocalDate dataPagamento) {

		    if (dataVencimento == null) {
		        throw new RuntimeException(
		                "A venda não possui data de vencimento"
		        );
		    }

		    if (dataPagamento == null) {
		        throw new RuntimeException(
		                "A data de pagamento é obrigatória"
		        );
		    }

		    if (!dataPagamento.isAfter(dataVencimento)) {
		        return BigDecimal.ZERO;
		    }

		    long diasAtraso = ChronoUnit.DAYS.between(
		            dataVencimento,
		            dataPagamento
		    );

		    BigDecimal taxaDiaria = BigDecimal.valueOf(0.025);

		    return valor
		            .multiply(taxaDiaria)
		            .multiply(BigDecimal.valueOf(diasAtraso));
		}
}

	


