package com.autobots.atv3.links;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.autobots.atv3.controles.EmpresaControle;
import com.autobots.atv3.entidades.Empresa;

@Component
public class EmpresaAdicionadorLink {
    public void adicionarLink(Empresa empresa) {
        Link selfLink = linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel();
        empresa.add(selfLink);

        Link allLink = linkTo(methodOn(EmpresaControle.class).obterEmpresas()).withRel("empresas");
        empresa.add(allLink);
    }

    public void adicionarLink(List<Empresa> empresas) {
        for (Empresa empresa : empresas) {
            adicionarLink(empresa);
        }
    }
}
