package com.target.entities.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendasListResponse {

    @Builder.Default
    private List<VendaListResponse> vendas = new ArrayList<>();
}