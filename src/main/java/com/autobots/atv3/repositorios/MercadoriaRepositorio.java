package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Mercadoria;

public interface MercadoriaRepositorio extends JpaRepository<Mercadoria, Long> {
    
}
