package com.target.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do vendedor é obrigatório")
    @Size(
        min = 3,
        max = 100,
        message = "O nome do vendedor deve ter entre 3 e 100 caracteres"
    )
    @Column(
        name = "NOME_VENDEDOR",
        nullable = false,
        length = 100
    )
    private String nome;

    @JsonBackReference
    @OneToMany(mappedBy = "vendedor")
    @Builder.Default
    @ToString.Exclude
    private List<Venda> vendas = new ArrayList<>();
}