package com.autobots.atv3.DTO;

import java.util.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private Set<Long> telefonesIds;
    private Long enderecoId;
    private Date cadastro;
    private Set<Long> usuariosIds;
    private Set<Long> mercadoriasIds;
    private Set<Long> servicosIds;
    private Set<Long> vendasIds;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public Set<Long> getTelefonesIds() { return telefonesIds; }
    public void setTelefonesIds(Set<Long> telefonesIds) { this.telefonesIds = telefonesIds; }

    public Long getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Long enderecoId) { this.enderecoId = enderecoId; }

    public Date getCadastro() { return cadastro; }
    public void setCadastro(Date cadastro) { this.cadastro = cadastro; }

    public Set<Long> getUsuariosIds() { return usuariosIds; }
    public void setUsuariosIds(Set<Long> usuariosIds) { this.usuariosIds = usuariosIds; }

    public Set<Long> getMercadoriasIds() { return mercadoriasIds; }
    public void setMercadoriasIds(Set<Long> mercadoriasIds) { this.mercadoriasIds = mercadoriasIds; }

    public Set<Long> getServicosIds() { return servicosIds; }
    public void setServicosIds(Set<Long> servicosIds) { this.servicosIds = servicosIds; }

    public Set<Long> getVendasIds() { return vendasIds; }
    public void setVendasIds(Set<Long> vendasIds) { this.vendasIds = vendasIds; }
}
