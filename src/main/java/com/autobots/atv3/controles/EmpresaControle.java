package com.autobots.atv3.controles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.EmpresaDTO;
import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.links.EmpresaAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
    @Autowired
    private EmpresaRepositorio repositorio;
    @Autowired
    private EmpresaAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obterEmpresa(@PathVariable Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresa);
            EmpresaDTO dto = converterParaDTO(empresa);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresas);
            List<EmpresaDTO> dtos = empresas.stream().map(this::converterParaDTO).toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<EmpresaDTO> cadastrarEmpresa(@RequestBody EmpresaDTO novaDTO) {
        Empresa nova = converterParaEntidade(novaDTO);
        Empresa salva = repositorio.save(nova);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(converterParaDTO(salva), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO atualizadaDTO) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Empresa atualizada = converterParaEntidade(atualizadaDTO);
        atualizada.setId(id);
        Empresa salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(converterParaDTO(salva), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(empresa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EmpresaDTO converterParaDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setNomeFantasia(empresa.getNomeFantasia());
        if (empresa.getTelefones() != null) {
            Set<Long> telefonesIds = empresa.getTelefones().stream()
                .map(telefone -> telefone.getId())
                .collect(Collectors.toSet());
            dto.setTelefonesIds(telefonesIds);
        } else {
            dto.setTelefonesIds(new HashSet<>());
        }
        dto.setEnderecoId(empresa.getEndereco() != null ? empresa.getEndereco().getId() : null);
        if (empresa.getUsuarios() != null) {
            Set<Long> usuariosIds = empresa.getUsuarios().stream()
                .map(usuario -> usuario.getId())
                .collect(Collectors.toSet());
            dto.setUsuariosIds(usuariosIds);
        } else {
            dto.setUsuariosIds(new HashSet<>());
        }
        if (empresa.getMercadorias() != null) {
            Set<Long> mercadoriasIds = empresa.getMercadorias().stream()
                .map(mercadoria -> mercadoria.getId())
                .collect(Collectors.toSet());
            dto.setMercadoriasIds(mercadoriasIds);
        } else {
            dto.setMercadoriasIds(new HashSet<>());
        }
        if (empresa.getServicos() != null) {
            Set<Long> servicosIds = empresa.getServicos().stream()
                .map(servico -> servico.getId())
                .collect(Collectors.toSet());
            dto.setServicosIds(servicosIds);
        } else {
            dto.setServicosIds(new HashSet<>());
        }
        if (empresa.getVendas() != null) {
            Set<Long> vendasIds = empresa.getVendas().stream()
                .map(venda -> venda.getId())
                .collect(Collectors.toSet());
            dto.setVendasIds(vendasIds);
        } else {
            dto.setVendasIds(new HashSet<>());
        }
        return dto;
    }

    private Empresa converterParaEntidade(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        return empresa;
    }
}
