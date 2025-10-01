package com.autobots.atv3.controles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.enumeracoes.PerfilUsuario;
import com.autobots.atv3.links.UsuarioAdicionadorLink;
import com.autobots.atv3.repositorios.EmpresaRepositorio;
import com.autobots.atv3.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
    @Autowired
    private UsuarioRepositorio repositorio;
    @Autowired
    private UsuarioAdicionadorLink adicionadorLink;
    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuarios);
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@PathVariable Long empresaId, @RequestBody Usuario novo) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        novo.setEmpresa(empresa);
        Usuario usuario = repositorio.save(novo);
        adicionadorLink.adicionarLink(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @PathVariable Long empresaId, @RequestBody Usuario atualizado) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (usuario == null || empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        atualizado.setId(id);
        atualizado.setEmpresa(empresa);
        Usuario salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.OK);
    }

    @PutMapping("/{id}/perfis")
    public ResponseEntity<Usuario> atualizarPerfis(@PathVariable Long id, @RequestBody Set<PerfilUsuario> novosPerfis) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usuario.setPerfis(novosPerfis);
        Usuario salvo = repositorio.save(usuario);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(salvo, HttpStatus.OK);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        repositorio.delete(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
