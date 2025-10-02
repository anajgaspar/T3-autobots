package com.autobots.atv3.DTO;

import java.util.Set;

import com.autobots.atv3.enumeracoes.PerfilUsuario;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	private Long id;
	private String nome;
	private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private Set<Long> telefonesIds;
    private Long enderecoId;
    private Set<Long> documentosIds;
    private Set<Long> emailsIds;
    private Set<Long> credenciaisIds;
    private Set<Long> mercadoriasIds;
    private Set<Long> vendasIds;
    private Set<Long> veiculosIds;
    private Long empresaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNomeSocial() { return nomeSocial; }
    public void setNomeSocial(String nomeSocial) { this.nomeSocial = nomeSocial; }

    public Set<PerfilUsuario> getPerfis() { return perfis; }
    public void setPerfis(Set<PerfilUsuario> perfis) { this.perfis = perfis; }

    public Set<Long> getTelefonesIds() { return telefonesIds; }
    public void setTelefonesIds(Set<Long> telefonesIds) { this.telefonesIds = telefonesIds; }

    public Long getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Long enderecoId) { this.enderecoId = enderecoId; }

    public Set<Long> getDocumentosIds() { return documentosIds; }
    public void setDocumentosIds(Set<Long> documentosIds) { this.documentosIds = documentosIds; }

    public Set<Long> getEmailsIds() { return emailsIds; }
    public void setEmailsIds(Set<Long> emailsIds) { this.emailsIds = emailsIds; }

    public Set<Long> getCredenciaisIds() { return credenciaisIds; }
    public void setCredenciaisIds(Set<Long> credenciaisIds) { this.credenciaisIds = credenciaisIds; }

    public Set<Long> getMercadoriasIds() { return mercadoriasIds; }
    public void setMercadoriasIds(Set<Long> mercadoriasIds) { this.mercadoriasIds = mercadoriasIds; }

    public Set<Long> getVendasIds() { return vendasIds; }
    public void setVendasIds(Set<Long> vendasIds) { this.vendasIds = vendasIds; }

    public Set<Long> getVeiculosIds() { return veiculosIds; }
    public void setVeiculosIds(Set<Long> veiculosIds) { this.veiculosIds = veiculosIds; }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
}
