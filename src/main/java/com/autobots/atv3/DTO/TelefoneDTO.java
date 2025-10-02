package com.autobots.atv3.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {
	private Long id;
	private String ddd;
	private String numero;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDdd() { return ddd; }
    public void setDdd(String ddd) { this.ddd = ddd; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
