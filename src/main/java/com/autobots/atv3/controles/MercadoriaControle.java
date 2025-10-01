package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.Mercadoria;
import com.autobots.atv3.links.MercadoriaAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;
import com.autobots.atv3.repositorios.MercadoriaRepositorio;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {
    @Autowired
    private MercadoriaRepositorio repositorio;
    @Autowired
    private MercadoriaAdicionadorLink adicionadorLink;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadoria);
            return new ResponseEntity<>(mercadoria, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadorias);
            return new ResponseEntity<>(mercadorias, HttpStatus.OK);
        }

    }

    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<Mercadoria> registrarMercadoria(@PathVariable Long empresaId, @RequestBody Mercadoria nova) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        nova.setEmpresa(empresa);
        Mercadoria mercadoria = repositorio.save(nova);
        adicionadorLink.adicionarLink(mercadoria);
        return new ResponseEntity<>(mercadoria, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Mercadoria> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria atualizada) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizada.setId(id);
        Mercadoria salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(salva, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Mercadoria> deletarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(mercadoria);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
