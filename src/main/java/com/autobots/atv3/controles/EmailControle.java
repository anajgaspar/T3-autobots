package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.EmailDTO;
import com.autobots.atv3.entidades.usuario.Email;
import com.autobots.atv3.links.EmailAdicionadorLink;
import com.autobots.atv3.repositorios.EmailRepositorio;

@RestController
@RequestMapping("/emails")
public class EmailControle {
    @Autowired
    private EmailRepositorio repositorio;
    @Autowired
    private EmailAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<EmailDTO> obterEmail(@PathVariable Long id) {
        Email email = repositorio.findById(id).orElse(null);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(email);
        EmailDTO dto = converterParaDTO(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmailDTO>> obterEmails() {
        List<Email> emails = repositorio.findAll();
        if (emails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(emails);
        List<EmailDTO> dtos = emails.stream().map(this::converterParaDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<EmailDTO> registrarEmail(@RequestBody EmailDTO novoDTO) {
        Email novo = converterParaEntidade(novoDTO);
        Email salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EmailDTO> atualizarEmail(@PathVariable Long id, @RequestBody EmailDTO atualizadoDTO) {
        Email email = repositorio.findById(id).orElse(null);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Email atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Email salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Email> deletarEmail(@PathVariable Long id) {
        Email email = repositorio.findById(id).orElse(null);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EmailDTO converterParaDTO(Email email) {
        EmailDTO dto = new EmailDTO();
        dto.setId(email.getId());
        dto.setEndereco(email.getEndereco());
        return dto;
    }

    private Email converterParaEntidade(EmailDTO dto) {
        Email email = new Email();
        email.setEndereco(dto.getEndereco());
        return email;
    }
}
