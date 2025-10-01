package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
    
}
