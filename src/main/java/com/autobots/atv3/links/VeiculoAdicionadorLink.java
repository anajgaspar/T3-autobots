package com.autobots.atv3.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.autobots.atv3.controles.VeiculoControle;
import com.autobots.atv3.entidades.Veiculo;

@Component
public class VeiculoAdicionadorLink {
    public void adicionarLink(Veiculo veiculo) {
        Link selfLink = linkTo(methodOn(VeiculoControle.class).obterVeiculo(veiculo.getId())).withSelfRel();
        veiculo.add(selfLink);

        Link allLink = linkTo(methodOn(VeiculoControle.class).obterVeiculos()).withRel("veiculos");
        veiculo.add(allLink);
    }

    public void adicionarLink(List<Veiculo> veiculos) {
        for (Veiculo veiculo : veiculos) {
            adicionarLink(veiculo);
        }
    }
}
