package com.autobots.atv3.entidades;

import java.util.*;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.enumeracoes.TipoVeiculo;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(exclude = { "proprietario", "vendas" }, callSuper = false)
public class Veiculo extends RepresentationModel<Veiculo> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private TipoVeiculo tipo;
	@Column(nullable = false)
	private String modelo;
	@Column(nullable = false)
	private String placa;
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Usuario proprietario;
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Venda> vendas = new HashSet<>();
}