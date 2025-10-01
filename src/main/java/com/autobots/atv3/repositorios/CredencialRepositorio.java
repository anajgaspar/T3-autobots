package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Credencial;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long> {
    
}
