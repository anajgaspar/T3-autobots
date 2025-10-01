package com.autobots.atv3.links;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import com.autobots.atv3.controles.MercadoriaControle;
import com.autobots.atv3.entidades.Mercadoria;

@Component
public class MercadoriaAdicionadorLink {
    public void adicionarLink (Mercadoria mercadoria) {
        Link selfLink = linkTo(methodOn(MercadoriaControle.class).obterMercadoria(mercadoria.getId())).withSelfRel();
        mercadoria.add(selfLink);

        Link allLink = linkTo(methodOn(MercadoriaControle.class).obterMercadorias()).withRel("mercadorias");
        mercadoria.add(allLink);
    }

    public void adicionarLink (List<Mercadoria> mercadorias) {
        for (Mercadoria mercadoria : mercadorias) {
            adicionarLink(mercadoria);
        }
    }
}
