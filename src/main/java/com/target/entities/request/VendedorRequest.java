
package com.target.entities.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendedorRequest {

    @NotBlank(message = "O nome do vendedor é obrigatório")
    @Size(
        min = 3,
        max = 100,
        message = "O nome do vendedor deve ter entre 3 e 100 caracteres"
    )
    private String nome;
}

