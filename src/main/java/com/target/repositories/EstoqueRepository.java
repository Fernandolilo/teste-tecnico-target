package com.target.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.target.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, UUID>{
	
	@Query("SELECT MAX(e.codigoProduto) FROM Estoque e")
    Integer encontrarMaiorCodigoProduto();

}
