package com.autobots.atv3.entidades.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.autobots.atv3.entidades.Credencial;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialUsuarioSenha extends Credencial {
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;
}