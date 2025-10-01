package com.autobots.atv3.entidades.usuario;

import java.util.Date;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)
public abstract class Credencial extends RepresentationModel<Credencial> {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date criacao;
	@Column()
	private Date ultimoAcesso;
	@Column(nullable = false)
	private boolean inativo;
	@ManyToOne
	private Usuario usuario;
}