package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.entidades.Veiculo;
import com.autobots.atv3.links.VeiculoAdicionadorLink;
import com.autobots.atv3.repositorios.VeiculoRepositorio;

@RestController
@RequestMapping("veiculos")
public class VeiculoControle {
    @Autowired
    private VeiculoRepositorio repositorio;
    @Autowired
    private VeiculoAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculo);
            return new ResponseEntity<>(veiculo, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculos);
            return new ResponseEntity<>(veiculos, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Veiculo> registrarVeiculo(@RequestBody Veiculo novo) {
        Veiculo veiculo = repositorio.save(novo);
        adicionadorLink.adicionarLink(veiculo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo atualizado) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        atualizado.setId(id);
        Veiculo salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Veiculo> deletarVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(veiculo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
