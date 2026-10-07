package com.target.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.entities.Venda;
import com.target.entities.request.VendaRequest;
import com.target.entities.response.VendaResponse;
import com.target.entities.response.VendasListResponse;
import com.target.service.VendaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService service;

    @PostMapping
    public ResponseEntity<Venda> criar(
            @RequestBody VendaRequest request) {

        Venda venda = service.crate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(venda);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaResponse> findById (@PathVariable UUID id){
    	return ResponseEntity.ok(service.findByVendaID(id));
    }
    
    @GetMapping
    public ResponseEntity<VendasListResponse> findAll() {

        return ResponseEntity.ok(service.findAll());
    }
}