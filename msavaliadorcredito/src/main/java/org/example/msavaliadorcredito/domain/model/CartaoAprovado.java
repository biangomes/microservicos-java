package org.example.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {
    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
