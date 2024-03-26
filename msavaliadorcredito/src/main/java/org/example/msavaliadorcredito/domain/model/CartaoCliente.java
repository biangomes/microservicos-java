package org.example.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoCliente {
  // Representa o retorno dos cartoes que aquele cliente têm
  private String nome;
  private String bandeira;
  private BigDecimal limiteLiberado;
}
