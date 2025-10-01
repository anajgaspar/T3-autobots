package com.autobots.atv3.entidades.usuario;

import java.util.Date;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.atv3.enumeracoes.TipoDocumento;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Documento extends RepresentationModel<Documento> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private TipoDocumento tipo;
	@Column(nullable = false)
	private Date dataEmissao;
	@Column(unique = true, nullable = false)
	private String numero;
}