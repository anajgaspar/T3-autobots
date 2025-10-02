package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.EnderecoDTO;
import com.autobots.atv3.entidades.usuario.Endereco;
import com.autobots.atv3.links.EnderecoAdicionadorLink;
import com.autobots.atv3.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private EnderecoAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> obterEndereco(@PathVariable Long id) {
        Endereco endereco = repositorio.findById(id).orElse(null);
        if (endereco == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(endereco);
        EnderecoDTO dto = converterParaDTO(endereco);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> obterEnderecos() {
        List<Endereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(enderecos);
        List<EnderecoDTO> dtos = enderecos.stream().map(this::converterParaDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<EnderecoDTO> registrarEndereco(@RequestBody EnderecoDTO novoDTO) {
        Endereco novo = converterParaEntidade(novoDTO);
        Endereco salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO atualizadoDTO) {
        Endereco endereco = repositorio.findById(id).orElse(null);
        if (endereco == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Endereco atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Endereco salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Endereco> deletarEndereco(@PathVariable Long id) {
        Endereco endereco = repositorio.findById(id).orElse(null);
        if (endereco == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(endereco);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EnderecoDTO converterParaDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(endereco.getId());
        dto.setEstado(endereco.getEstado());
        dto.setCidade(endereco.getCidade());
        dto.setBairro(endereco.getBairro());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        dto.setCodigoPostal(endereco.getCodigoPostal());
        dto.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
        return dto;
    }

    private Endereco converterParaEntidade(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCodigoPostal());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());
        return endereco;
    }
}
