package com.autobots.atv3.entidades.usuario;

import javax.persistence.*;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CredencialCodigoBarra extends Credencial {
	@Column(nullable = false, unique = true)
	private long codigo;
}