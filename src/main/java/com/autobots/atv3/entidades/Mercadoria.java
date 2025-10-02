package com.autobots.atv3.entidades;

import java.util.Date;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Mercadoria extends RepresentationModel<Mercadoria> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date validade;
	@Column(nullable = false)
	private Date fabricacao;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private long quantidade;
	@Column(nullable = false)
	private double valor;
	@Column()
	private String descricao;
	@ManyToOne
	private Empresa empresa;
}