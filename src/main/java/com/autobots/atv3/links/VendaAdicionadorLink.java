package com.autobots.atv3.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.autobots.atv3.controles.VendaControle;
import com.autobots.atv3.entidades.Venda;

@Component
public class VendaAdicionadorLink {
    public void adicionarLink(Venda venda) {
        Link selfLink = linkTo(methodOn(VendaControle.class).obterVenda(venda.getId())).withSelfRel();
        venda.add(selfLink);

        Link allLink = linkTo(methodOn(VendaControle.class).obterVendas()).withRel("vendas");
        venda.add(allLink);
    }

    public void adicionarLink(List<Venda> vendas) {
        for (Venda venda : vendas) {
            adicionarLink(venda);
        }
    }
}
