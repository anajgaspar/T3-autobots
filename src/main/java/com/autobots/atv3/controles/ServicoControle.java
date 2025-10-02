package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.ServicoDTO;
import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.Servico;
import com.autobots.atv3.links.ServicoAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;
import com.autobots.atv3.repositorios.ServicoRepositorio;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {
    @Autowired
    private ServicoRepositorio repositorio;
    @Autowired
    private ServicoAdicionadorLink adicionadorLink;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> obterServico(@PathVariable Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(servico);
            ServicoDTO dto = converterParaDTO(servico);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(servicos);
        List<ServicoDTO> dtos = servicos.stream().map(this::converterParaDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<ServicoDTO> registrarServico(@PathVariable Long empresaId, @RequestBody ServicoDTO novoDTO) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Servico novo = converterParaEntidade(novoDTO);
        novo.setEmpresa(empresa);
        Servico salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ServicoDTO> atualizarServico(@PathVariable Long id, @RequestBody ServicoDTO atualizadoDTO) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Servico atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Servico salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Servico> deletarServico(@PathVariable Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(servico);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ServicoDTO converterParaDTO(Servico servico) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setValor(servico.getValor());
        dto.setDescricao(servico.getDescricao());
        dto.setEmpresaId(servico.getEmpresa() != null ? servico.getEmpresa().getId() : null);
        return dto;
    }

    private Servico converterParaEntidade(ServicoDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setValor(dto.getValor());
        servico.setDescricao(dto.getDescricao());
        return servico;
    }
}
