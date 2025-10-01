package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.usuario.Documento;

public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
    
}
