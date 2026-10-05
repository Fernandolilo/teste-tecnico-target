package com.target.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.target.entities.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, UUID>{

}
