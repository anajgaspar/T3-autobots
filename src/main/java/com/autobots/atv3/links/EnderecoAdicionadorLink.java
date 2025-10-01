package com.autobots.atv3.links;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.autobots.atv3.controles.EnderecoControle;
import com.autobots.atv3.entidades.usuario.Endereco;

@Component
public class EnderecoAdicionadorLink {
    public void adicionarLink(Endereco endereco) {
        Link selfLink = linkTo(methodOn(EnderecoControle.class).obterEndereco(endereco.getId())).withSelfRel();
        endereco.add(selfLink);

        Link allLink = linkTo(methodOn(EnderecoControle.class).obterEnderecos()).withRel("enderecos");
        endereco.add(allLink);
    }

    public void adicionarLink(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            adicionarLink(endereco);
        }
    }
}
