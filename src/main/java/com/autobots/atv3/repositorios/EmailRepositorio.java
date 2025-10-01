package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.usuario.Email;

public interface EmailRepositorio extends JpaRepository<Email, Long> {
    
}
