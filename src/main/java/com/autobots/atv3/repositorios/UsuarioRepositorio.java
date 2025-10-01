package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.usuario.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
}
