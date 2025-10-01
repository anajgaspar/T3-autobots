package com.autobots.atv3.links;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.autobots.atv3.controles.TelefoneControle;
import com.autobots.atv3.entidades.usuario.Telefone;

@Component
public class TelefoneAdicionadorLink {
    public void adicionarLink(Telefone telefone) {
        Link selfLink = linkTo(methodOn(TelefoneControle.class).obterTelefone(telefone.getId())).withSelfRel();
        telefone.add(selfLink);

        Link allLink = linkTo(methodOn(TelefoneControle.class).obterTelefones()).withRel("telefones");
        telefone.add(allLink);
    }

    public void adicionarLink(List<Telefone> telefones) {
        for (Telefone telefone : telefones) {
            adicionarLink(telefone);
        }
    }
}
