package rh.docket.desafio.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartorioDTO {

    private Long   id;
    private String nome;
    private String endereco;
    private Set<Long> idCertidoes;

}