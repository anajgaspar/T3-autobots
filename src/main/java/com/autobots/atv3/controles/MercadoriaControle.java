package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.MercadoriaDTO;
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
    public ResponseEntity<MercadoriaDTO> obterMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadoria);
            MercadoriaDTO dto = converterParaDTO(mercadoria);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<MercadoriaDTO>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        if (mercadorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(mercadorias);
            List<MercadoriaDTO> dtos = mercadorias.stream().map(this::converterParaDTO).toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }

    }

    @PostMapping("/registrar/{empresaId}")
    public ResponseEntity<MercadoriaDTO> registrarMercadoria(@PathVariable Long empresaId, @RequestBody MercadoriaDTO novaDTO) {
        Empresa empresa = empresaRepositorio.findById(empresaId).orElse(null);
        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Mercadoria nova = converterParaEntidade(novaDTO);
        nova.setEmpresa(empresa);
        Mercadoria salva = repositorio.save(nova);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(converterParaDTO(salva), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MercadoriaDTO> atualizarMercadoria(@PathVariable Long id, @RequestBody MercadoriaDTO atualizadaDTO) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Mercadoria atualizada = converterParaEntidade(atualizadaDTO);
        atualizada.setId(id);
        Mercadoria salva = repositorio.save(atualizada);
        adicionadorLink.adicionarLink(salva);
        return new ResponseEntity<>(converterParaDTO(salva), HttpStatus.OK);
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

    private MercadoriaDTO converterParaDTO(Mercadoria mercadoria) {
        MercadoriaDTO dto = new MercadoriaDTO();
        dto.setId(mercadoria.getId());
        dto.setValidade(mercadoria.getValidade());
        dto.setFabricacao(mercadoria.getFabricacao());
        dto.setCadastro(mercadoria.getCadastro());
        dto.setNome(mercadoria.getNome());
        dto.setQuantidade(mercadoria.getQuantidade());
        dto.setValor(mercadoria.getValor());
        dto.setDescricao(mercadoria.getDescricao());
        dto.setEmpresaId(mercadoria.getEmpresa() != null ? mercadoria.getEmpresa().getId() : null);
        return dto;
    }

    private Mercadoria converterParaEntidade(MercadoriaDTO dto) {
        Mercadoria mercadoria = new Mercadoria();
        mercadoria.setValidade(dto.getValidade());
        mercadoria.setFabricacao(dto.getFabricacao());
        mercadoria.setCadastro(dto.getCadastro());
        mercadoria.setNome(dto.getNome());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setValor(dto.getValor());
        mercadoria.setDescricao(dto.getDescricao());
        return mercadoria;
    }
}
