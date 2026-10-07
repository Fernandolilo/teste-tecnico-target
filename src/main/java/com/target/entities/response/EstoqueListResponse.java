package com.target.entities.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EstoqueListResponse {

    @Builder.Default
    private List<EstoqueResponse> estoque = new ArrayList<>();
}