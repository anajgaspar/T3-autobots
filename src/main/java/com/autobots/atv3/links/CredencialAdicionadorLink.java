package com.autobots.atv3.links;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.autobots.atv3.controles.CredencialControle;
import com.autobots.atv3.entidades.usuario.Credencial;

@Component
public class CredencialAdicionadorLink {
    public void adicionarLink(Credencial credencial) {
        Link selfLink = linkTo(methodOn(CredencialControle.class).inativarCredencial(credencial.getId())).withSelfRel();
        credencial.add(selfLink);

        if (credencial.getUsuario() != null) {
            Link allLink = linkTo(methodOn(CredencialControle.class).listarCredenciais(credencial.getUsuario().getId())).withRel("credenciaisUsuario");
            credencial.add(allLink);
        }
    }

    public void adicionarLink(List<Credencial> credenciais) {
        for (Credencial credencial : credenciais) {
            adicionarLink(credencial);
        }
    }
}
