package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Email> obterEmail(@PathVariable Long id) {
        Email email = repositorio.findById(id).orElse(null);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(email);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Email>> obterEmails() {
        List<Email> emails = repositorio.findAll();
        if (emails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(emails);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Email> registrarEmail(@RequestBody Email novo) {
        Email salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Email> atualizarEmail(@PathVariable Long id, @RequestBody Email atualizado) {
        Email email = repositorio.findById(id).orElse(null);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizado.setId(id);
        Email salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.OK);
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
}
