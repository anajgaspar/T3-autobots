package com.autobots.atv3.links;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import com.autobots.atv3.controles.EmailControle;
import com.autobots.atv3.entidades.usuario.Email;

@Component
public class EmailAdicionadorLink {
    public void adicionarLink(Email email) {
        Link selfLink = linkTo(methodOn(EmailControle.class).obterEmail(email.getId())).withSelfRel();
        email.add(selfLink);

        Link allLink = linkTo(methodOn(EmailControle.class).obterEmails()).withRel("emails");
        email.add(allLink);
    }

    public void adicionarLink(List<Email> emails) {
        for (Email email : emails) {
            adicionarLink(email);
        }
    }
}
