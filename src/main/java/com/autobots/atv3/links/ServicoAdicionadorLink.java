package com.autobots.atv3.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.autobots.atv3.controles.ServicoControle;
import com.autobots.atv3.entidades.Servico;

@Component
public class ServicoAdicionadorLink {
    public void adicionarLink (Servico servico) {
        Link selfLink = linkTo(methodOn(ServicoControle.class).obterServico(servico.getId())).withSelfRel();
        servico.add(selfLink);

        Link allLink = linkTo(methodOn(ServicoControle.class).obterServicos()).withRel("servicos");
        servico.add(allLink);
    }

    public void adicionarLink (List<Servico> servicos) {
        for (Servico servico : servicos) {
            adicionarLink(servico);
        }
    }
}
