package com.autobots.atv3.controles;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.autobots.atv3.entidades.usuario.Credencial;
import com.autobots.atv3.entidades.usuario.CredencialCodigoBarra;
import com.autobots.atv3.entidades.usuario.CredencialUsuarioSenha;
import com.autobots.atv3.entidades.usuario.Usuario;
import com.autobots.atv3.links.CredencialAdicionadorLink;
import com.autobots.atv3.repositorios.CredencialRepositorio;
import com.autobots.atv3.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {
    @Autowired
    private CredencialRepositorio repositorio;
    @Autowired
    private CredencialAdicionadorLink adicionadorLink;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Credencial>> listarCredenciais(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Credencial> credenciais = usuario.getCredenciais();
        if (credenciais.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Credencial> lista = credenciais.stream().collect(Collectors.toList());
        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }   

    @PostMapping("/usuario-senha/{usuarioId}")
    public ResponseEntity<CredencialUsuarioSenha> registrarUsuarioSenha(@PathVariable Long usuarioId, @RequestBody CredencialUsuarioSenha novaCredencial) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        novaCredencial.setCriacao(new Date());
        novaCredencial.setInativo(false);
        novaCredencial.setSenha(encoder.encode(novaCredencial.getSenha()));
        novaCredencial.setUsuario(usuario);
        usuario.getCredenciais().add(novaCredencial);
        usuarioRepositorio.save(usuario);
        adicionadorLink.adicionarLink(novaCredencial);
        return new ResponseEntity<>(novaCredencial, HttpStatus.CREATED);
    }

    @PostMapping("/codigo-barra/{usuarioId}")
    public ResponseEntity<CredencialCodigoBarra> registrarCodigoBarra(@PathVariable Long usuarioId, @RequestBody CredencialCodigoBarra novaCredencial) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        novaCredencial.setCriacao(new Date());
        novaCredencial.setInativo(false);
        novaCredencial.setUsuario(usuario);
        usuario.getCredenciais().add(novaCredencial);
        usuarioRepositorio.save(usuario);
        adicionadorLink.adicionarLink(novaCredencial);
        return new ResponseEntity<>(novaCredencial, HttpStatus.CREATED);
    }

    @PatchMapping("/{credencialId}/inativar")
    public ResponseEntity<Credencial> inativarCredencial(@PathVariable Long credencialId) {
        Credencial credencial = repositorio.findById(credencialId).orElse(null);
        if (credencial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        credencial.setInativo(true);
        repositorio.save(credencial);
        adicionadorLink.adicionarLink(credencial);
        return new ResponseEntity<>(credencial, HttpStatus.OK);
    }
}
