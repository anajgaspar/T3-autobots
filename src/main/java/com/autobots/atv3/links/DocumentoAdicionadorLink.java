package com.autobots.atv3.links;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.autobots.atv3.controles.DocumentoControle;
import com.autobots.atv3.entidades.usuario.Documento;

@Component
public class DocumentoAdicionadorLink {
    public void adicionarLink(Documento documento) {
        Link selfLink = linkTo(methodOn(DocumentoControle.class).obterDocumento(documento.getId())).withSelfRel();
        documento.add(selfLink);

        Link allLink = linkTo(methodOn(DocumentoControle.class).obterDocumentos()).withRel("documentos");
        documento.add(allLink);
    }

    public void adicionarLink(List<Documento> documentos) {
        for (Documento documento : documentos) {
            adicionarLink(documento);
        }
    }
}
