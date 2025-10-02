package com.autobots.atv3.controles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.VendaDTO;
import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.Veiculo;
import com.autobots.atv3.entidades.Venda;
import com.autobots.atv3.entidades.Mercadoria;
import com.autobots.atv3.entidades.Servico;
import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.links.VendaAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;
import com.autobots.atv3.repositorios.UsuarioRepositorio;
import com.autobots.atv3.repositorios.VeiculoRepositorio;
import com.autobots.atv3.repositorios.VendaRepositorio;

@RestController
@RequestMapping("/vendas")
public class VendaControle {
    @Autowired
    private VendaRepositorio repositorio;
    @Autowired
    private VendaAdicionadorLink adicionadorLink;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Autowired 
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> obterVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(venda);
            VendaDTO dto = converterParaDTO(venda);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(vendas);
            List<VendaDTO> dtos = vendas.stream().map(this::converterParaDTO).toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar/{empresaId}/{clienteId}")
    public ResponseEntity<VendaDTO> registrarVenda(@PathVariable Long empresaId, @PathVariable Long clienteId, @RequestParam(required = false) Long veiculoId, @RequestBody VendaDTO novaDTO) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        Usuario cliente = usuarioRepositorio.findById(clienteId).orElse(null);
        Veiculo veiculo = (veiculoId != null) ? veiculoRepositorio.findById(veiculoId).orElse(null) : null;
        if (empresa == null || cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Venda nova = converterParaEntidade(novaDTO);
        nova.setEmpresa(empresa);
        nova.setCliente(cliente);
        nova.setVeiculo(veiculo);
        Venda venda = repositorio.save(nova);
        adicionadorLink.adicionarLink(venda);
        return new ResponseEntity<>(converterParaDTO(venda), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<VendaDTO> atualizarVenda(@PathVariable Long id, @RequestBody VendaDTO atualizadaDTO) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Venda atualizada = converterParaEntidade(atualizadaDTO);
        atualizada.setId(id);
        Venda salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(converterParaDTO(salva), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Venda> deletarVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(venda);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private VendaDTO converterParaDTO(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setCadastro(venda.getCadastro());
        dto.setIdentificacao(venda.getIdentificacao());
        dto.setClienteId(venda.getCliente() != null ? venda.getFuncionario().getId() : null);
        dto.setFuncionarioId(venda.getFuncionario() != null ? venda.getFuncionario().getId() : null);
        dto.setMercadoriasIds(venda.getMercadorias().stream().map(Mercadoria::getId).collect(Collectors.toSet()));
        dto.setServicosIds(venda.getServicos().stream().map(Servico::getId).collect(Collectors.toSet()));
        dto.setVeiculoId(venda.getVeiculo() != null ? venda.getVeiculo().getId() : null);
        dto.setEmpresaId(venda.getEmpresa() != null ? venda.getEmpresa().getId() : null);
        return dto;
    }

    private Venda converterParaEntidade(VendaDTO dto) {
        Venda venda = new Venda();
        venda.setCadastro(dto.getCadastro());
        venda.setIdentificacao(dto.getIdentificacao());
        return venda;
    }
}
