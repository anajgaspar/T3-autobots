package com.autobots.atv3.DTO;

import java.util.Set;

import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.enumeracoes.TipoVeiculo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {
	private Long id;
    private TipoVeiculo tipo;
	private String modelo;
	private String placa;
	private Usuario proprietario;
    private Set<Long> vendasIds;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoVeiculo getTipo() { return tipo; }
    public void setTipo(TipoVeiculo tipo) { this.tipo = tipo; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Usuario getProprietario() { return proprietario; }
    public void setProprietario(Usuario proprietario) { this.proprietario = proprietario; }

    public Set<Long> getVendasIds() { return vendasIds; }
    public void setVendasIds(Set<Long> vendasIds) { this.vendasIds = vendasIds; }
}
