package org.example.msavaliadorcredito.domain.model;

import lombok.Data;

@Data
public class DadosCliente {
  // objeto representacional da requisicao/transacao da classe Cliente (msclientes)
  // equivalente aos DTOs
  private Long id;
  private String nome;
  private Integer idade;
}
