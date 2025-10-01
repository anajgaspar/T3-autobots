package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.Veiculo;
import com.autobots.atv3.entidades.Venda;
import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.links.VendaAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;
import com.autobots.atv3.repositorios.UsuarioRepositorio;
import com.autobots.atv3.repositorios.VeiculoRepositorio;
import com.autobots.atv3.repositorios.VendaRepositorio;

@RestController
@RequestMapping("/vendas")
public class VendaControle {
    @Autowired
    private VendaRepositorio repositorio;
    @Autowired
    private VendaAdicionadorLink adicionadorLink;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Autowired 
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private VeiculoRepositorio veiculoRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(venda);
            return new ResponseEntity<>(venda, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        if (vendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(vendas);
            return new ResponseEntity<>(vendas, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar/{empresaId}/{clienteId}")
    public ResponseEntity<Venda> registrarVenda(@PathVariable Long empresaId, @PathVariable Long clienteId, @RequestParam(required = false) Long veiculoId, @RequestBody Venda nova) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        Usuario cliente = usuarioRepositorio.findById(clienteId).orElse(null);
        Veiculo veiculo = (veiculoId != null) ? veiculoRepositorio.findById(veiculoId).orElse(null) : null;
        if (empresa == null || cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        nova.setEmpresa(empresa);
        nova.setCliente(cliente);
        nova.setVeiculo(veiculo);
        Venda venda = repositorio.save(nova);
        adicionadorLink.adicionarLink(venda);
        return new ResponseEntity<>(venda, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Venda> atualizarVenda(@PathVariable Long id, @RequestBody Venda atualizada) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizada.setId(id);
        Venda salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(salva, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Venda> deletarVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(venda);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
