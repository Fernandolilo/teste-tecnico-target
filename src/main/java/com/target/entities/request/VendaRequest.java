package com.target.entities.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendaRequest {
	
	@Column(name = "DATA_VENDA")
	private LocalDate instante;	
  
	private UUID vendedor;
	
	private LocalDate dataVencimento;
    
	  @Builder.Default
	    private List<ItemVendaRequest> produtos =
	        new ArrayList<>();

	  
	  
}
