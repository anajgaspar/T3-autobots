package com.autobots.atv3.DTO;

import java.util.Date;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private Date cadastro;
    private String identificacao;
    private Long clienteId;
    private Long funcionarioId;
    private Set<Long> mercadoriasIds;
    private Set<Long> servicosIds;
    private Long veiculoId;
    private Long empresaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getCadastro() { return cadastro; }
    public void setCadastro(Date cadastro) { this.cadastro = cadastro; }

    public String getIdentificacao() { return identificacao; }
    public void setIdentificacao(String identificacao) { this.identificacao = identificacao; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public Set<Long> getMercadoriasIds() { return mercadoriasIds; }
    public void setMercadoriasIds(Set<Long> mercadoriasIds) { this.mercadoriasIds = mercadoriasIds; }

    public Set<Long> getServicosIds() { return servicosIds; }
    public void setServicosIds(Set<Long> servicosIds) { this.servicosIds = servicosIds; }

    public Long getVeiculoId() { return veiculoId; }
    public void setVeiculoId(Long veiculoId) { this.veiculoId = veiculoId; }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
}
