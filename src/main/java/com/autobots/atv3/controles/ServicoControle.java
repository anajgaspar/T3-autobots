package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(servico);
            return new ResponseEntity<>(servico, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Servico>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        if (servicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(servicos);
        return new ResponseEntity<>(servicos, HttpStatus.OK);
    }

    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<Servico> registrarServico(@PathVariable Long empresaId, @RequestBody Servico novo) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        novo.setEmpresa(empresa);
        Servico servico = repositorio.save(novo);
        adicionadorLink.adicionarLink(servico);
        return new ResponseEntity<>(servico, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico atualizado) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizado.setId(id);
        Servico salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.OK);
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
}
