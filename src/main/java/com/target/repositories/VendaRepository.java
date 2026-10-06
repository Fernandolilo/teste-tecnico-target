package com.target.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.target.entities.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID>{

}
