package org.example.msavaliadorcredito.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DadosCliente {
  // objeto representacional da requisicao/transacao da classe Cliente (msclientes)
  // equivalente aos DTOs
  @JsonProperty("id")
  private Long id;
  @JsonProperty("nome")
  private String nome;
  @JsonProperty("idade")
  private Integer idade;
}
