package com.target.service.implTets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.target.service.impl.VendaServiceImpl;

class VendaServiceImplTest {

    private VendaServiceImpl service;

    @BeforeEach
    void setUp() {

        service = new VendaServiceImpl(
                null,
                null,
                null,
                null
        );
    }

    @Test
    void deveRetornarZeroParaVendaAbaixoDe100() {

        BigDecimal resultado =
                service.calculateCommission(
                        new BigDecimal("99.99")
                );

        assertEquals(
                0,
                resultado.compareTo(new BigDecimal("0"))
        );
    }

    @Test
    void deveCalcular1PorcentoParaVendaEntre100E500() {

        BigDecimal resultado =
                service.calculateCommission(
                        new BigDecimal("200.00")
                );

        assertEquals(
                0,
                resultado.compareTo(new BigDecimal("2.00"))
        );
    }

    @Test
    void deveCalcular5PorcentoParaVendaAPartirDe500() {

        BigDecimal resultado =
                service.calculateCommission(
                        new BigDecimal("500.00")
                );

        assertEquals(
                0,
                resultado.compareTo(new BigDecimal("25.00"))
        );
    }

    @Test
    void deveCalcular5PorcentoParaVendaDe1000() {

        BigDecimal resultado =
                service.calculateCommission(
                        new BigDecimal("1000.00")
                );

        assertEquals(
                0,
                resultado.compareTo(new BigDecimal("50.00"))
        );
    }
}
