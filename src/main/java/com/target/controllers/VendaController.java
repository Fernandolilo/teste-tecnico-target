package com.target.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.target.entities.Venda;
import com.target.entities.request.VendaRequest;
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
}