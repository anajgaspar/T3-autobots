package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Servico;

public interface ServicoRepositorio extends JpaRepository<Servico, Long> {
    
}
