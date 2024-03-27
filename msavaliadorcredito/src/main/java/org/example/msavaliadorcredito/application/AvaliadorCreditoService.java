package org.example.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.domain.model.CartaoCliente;
import org.example.msavaliadorcredito.domain.model.DadosCliente;
import org.example.msavaliadorcredito.domain.model.SituacaoCliente;
import org.example.msavaliadorcredito.infra.clients.CartoesResourceClient;
import org.example.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
  private final ClienteResourceClient clientesClient;
  private final CartoesResourceClient cartoesClient;

  public SituacaoCliente obterSituacaoCliente(String cpf) {
    // obterDadosClientes (msclientes)
    ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
    ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

    // obter cartoes do cliente (mscartoes)
    return SituacaoCliente.builder()
        .cliente(dadosClienteResponse.getBody())
            .cartoes(cartoesResponse.getBody())
        .build();
  }

}
