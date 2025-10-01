package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.usuario.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {
    
}
