package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresa);
            return new ResponseEntity<>(empresa, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        List<Empresa> empresas = repositorio.findAll();
        if (empresas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(empresas);
            return new ResponseEntity<>(empresas, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa nova) {
        Empresa empresa = repositorio.save(nova);
        adicionadorLink.adicionarLink(empresa);
        return new ResponseEntity<>(empresa, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa atualizada) {
        Empresa empresa = repositorio.findById(id).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizada.setId(id);
        Empresa salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(salva, HttpStatus.OK);
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
}
