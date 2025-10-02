package com.autobots.atv3.controles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.autobots.atv3.DTO.VeiculoDTO;
import com.autobots.atv3.entidades.Veiculo;
import com.autobots.atv3.entidades.Venda;
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
    public ResponseEntity<VeiculoDTO> obterVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculo);
            VeiculoDTO dto = converterParaDTO(veiculo);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        if (veiculos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(veiculos);
            List<VeiculoDTO> dtos = veiculos.stream().map(this::converterParaDTO).toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<VeiculoDTO> registrarVeiculo(@RequestBody VeiculoDTO novoDTO) {
        Veiculo novo = converterParaEntidade(novoDTO);
        Veiculo salvo = repositorio.save(novo);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO atualizadoDTO) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Veiculo atualizado = converterParaEntidade(atualizadoDTO);
        atualizado.setId(id);
        Veiculo salvo = repositorio.save(atualizado);
        adicionadorLink.adicionarLink(salvo);
        return new ResponseEntity<>(converterParaDTO(salvo), HttpStatus.OK);
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

    private VeiculoDTO converterParaDTO(Veiculo veiculo) {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setModelo(veiculo.getModelo());
        dto.setPlaca(veiculo.getPlaca());
        dto.setProprietario(veiculo.getProprietario());
        dto.setTipo(veiculo.getTipo());
        dto.setVendasIds(veiculo.getVendas().stream().map(Venda::getId).collect(Collectors.toSet()));
        return dto;
    }

    private Veiculo converterParaEntidade(VeiculoDTO dto) {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(dto.getModelo());
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setProprietario(dto.getProprietario());
        veiculo.setTipo(dto.getTipo());
        return veiculo;
    }
}
