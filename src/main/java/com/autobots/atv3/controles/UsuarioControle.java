package com.autobots.atv3.controles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.UsuarioDTO;
import com.autobots.atv3.entidades.Empresa;
import com.autobots.atv3.entidades.Mercadoria;
import com.autobots.atv3.entidades.Veiculo;
import com.autobots.atv3.entidades.Venda;
import com.autobots.atv3.entidades.usuario.Credencial;
import com.autobots.atv3.entidades.usuario.Documento;
import com.autobots.atv3.entidades.usuario.Email;
import com.autobots.atv3.entidades.usuario.Telefone;
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
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuario);
            UsuarioDTO dto = converterParaDTO(usuario);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(usuarios);
            List<UsuarioDTO> dtos = usuarios.stream().map(this::converterParaDTO).toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@PathVariable Long empresaId, @RequestBody UsuarioDTO novoDTO) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Usuario novo = converterParaEntidade(novoDTO);
        novo.setEmpresa(empresa);
        Usuario salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @PathVariable Long empresaId, @RequestBody UsuarioDTO atualizadoDTO) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (usuario == null || empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Usuario atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        atualizado.setEmpresa(empresa);
        Usuario salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @PutMapping("/{id}/perfis")
    public ResponseEntity<UsuarioDTO> atualizarPerfis(@PathVariable Long id, @RequestBody Set<PerfilUsuario> novosPerfis) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usuario.setPerfis(novosPerfis);
        Usuario salvo = repositorio.save(usuario);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
        repositorio.delete(usuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setNomeSocial(usuario.getNomeSocial());
        dto.setPerfis(usuario.getPerfis());
        dto.setTelefonesIds(usuario.getTelefones().stream().map(Telefone::getId).collect(Collectors.toSet()));
        dto.setDocumentosIds(usuario.getDocumentos().stream().map(Documento::getId).collect(Collectors.toSet()));
        dto.setEmailsIds(usuario.getEmails().stream().map(Email::getId).collect(Collectors.toSet()));
        dto.setCredenciaisIds(usuario.getCredenciais().stream().map(Credencial::getId).collect(Collectors.toSet()));
        dto.setMercadoriasIds(usuario.getMercadorias().stream().map(Mercadoria::getId).collect(Collectors.toSet()));
        dto.setVendasIds(usuario.getVendas().stream().map(Venda::getId).collect(Collectors.toSet()));
        dto.setVeiculosIds(usuario.getVeiculos().stream().map(Veiculo::getId).collect(Collectors.toSet()));
        dto.setEmpresaId(usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null);
        return dto;
    }

    private Usuario converterParaEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setNomeSocial(dto.getNomeSocial());
        usuario.setPerfis(dto.getPerfis());
        return usuario;
    }
}
