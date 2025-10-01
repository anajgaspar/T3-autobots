package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
    
}
