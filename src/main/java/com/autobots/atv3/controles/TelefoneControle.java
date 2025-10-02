package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.TelefoneDTO;
import com.autobots.atv3.entidades.usuario.Telefone;
import com.autobots.atv3.links.TelefoneAdicionadorLink;
import com.autobots.atv3.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private TelefoneAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> obterTelefone(@PathVariable Long id) {
        Telefone telefone = repositorio.findById(id).orElse(null);
        if (telefone == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(telefone);
        TelefoneDTO dto = converterParaDTO(telefone);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TelefoneDTO>> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(telefones);
        List<TelefoneDTO> dtos = telefones.stream().map(this::converterParaDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<TelefoneDTO> registrarTelefone(@RequestBody TelefoneDTO novoDTO) {
        Telefone novo = converterParaEntidade(novoDTO);
        Telefone salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@PathVariable Long id, @RequestBody TelefoneDTO atualizadoDTO) {
        Telefone telefone = repositorio.findById(id).orElse(null);
        if (telefone == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Telefone atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Telefone salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Telefone> deletarTelefone(@PathVariable Long id) {
        Telefone telefone = repositorio.findById(id).orElse(null);
        if (telefone == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(telefone);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TelefoneDTO converterParaDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setId(telefone.getId());
        dto.setDdd(telefone.getDdd());
        dto.setNumero(telefone.getNumero());
        return dto;
    }

    private Telefone converterParaEntidade(TelefoneDTO dto) {
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.getDdd());
        telefone.setNumero(dto.getNumero());
        return telefone;
    }
}
