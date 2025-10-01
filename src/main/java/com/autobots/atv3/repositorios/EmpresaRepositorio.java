package com.autobots.atv3.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atv3.entidades.Empresa;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
	//public Empresa findByRazaoSocial(String nome);
}