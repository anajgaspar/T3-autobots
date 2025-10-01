package com.autobots.atv3.entidades;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Servico extends RepresentationModel<Servico> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private double valor;
	@Column
	private String descricao;
	@ManyToOne
	private Empresa empresa;
}