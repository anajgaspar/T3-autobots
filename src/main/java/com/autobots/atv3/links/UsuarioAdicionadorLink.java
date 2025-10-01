package com.autobots.atv3.links;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import java.util.List;

import com.autobots.atv3.controles.UsuarioControle;
import com.autobots.atv3.entidades.usuario.Usuario;

@Component
public class UsuarioAdicionadorLink {
    public void adicionarLink(Usuario usuario) {
        Link selfLink = linkTo(methodOn(UsuarioControle.class).obterUsuario(usuario.getId())).withSelfRel();
        usuario.add(selfLink);

        Link allLink = linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios");
        usuario.add(allLink);
    }

    public void adicionarLink(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            adicionarLink(usuario);
        }
    }
}
