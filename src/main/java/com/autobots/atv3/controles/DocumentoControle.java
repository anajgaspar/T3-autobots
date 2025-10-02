package com.autobots.atv3.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.DocumentoDTO;
import com.autobots.atv3.entidades.usuario.Documento;
import com.autobots.atv3.links.DocumentoAdicionadorLink;
import com.autobots.atv3.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    private DocumentoAdicionadorLink adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable Long id) {
        Documento documento = repositorio.findById(id).orElse(null);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(documento);
        DocumentoDTO dto = converterParaDTO(documento);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(documentos);
        List<DocumentoDTO> dtos = documentos.stream().map(this::converterParaDTO).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<DocumentoDTO> registrarDocumento(@RequestBody DocumentoDTO novoDTO) {
        Documento novo = converterParaEntidade(novoDTO);
        Documento salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<DocumentoDTO> atualizarDocumento(@PathVariable Long id, @RequestBody DocumentoDTO atualizadoDTO) {
        Documento documento = repositorio.findById(id).orElse(null);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Documento atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Documento salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Documento> deletarDocumento(@PathVariable Long id) {
        Documento documento = repositorio.findById(id).orElse(null);
        if (documento == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositorio.delete(documento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DocumentoDTO converterParaDTO(Documento documento) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(documento.getId());
        dto.setTipo(documento.getTipo());
        dto.setDataEmissao(documento.getDataEmissao());
        dto.setNumero(documento.getNumero());
        return dto;
    }

    private Documento converterParaEntidade(DocumentoDTO dto) {
        Documento documento = new Documento();
        documento.setTipo(dto.getTipo());
        documento.setDataEmissao(dto.getDataEmissao());
        documento.setNumero(dto.getNumero());
        return documento;
    }
}
