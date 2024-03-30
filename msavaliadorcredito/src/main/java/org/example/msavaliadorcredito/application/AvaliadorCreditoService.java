package org.example.msavaliadorcredito.application;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import org.example.msavaliadorcredito.application.exceptions.ErroComunicacaoMicrosservicesException;
import org.example.msavaliadorcredito.domain.model.CartaoCliente;
import org.example.msavaliadorcredito.domain.model.DadosCliente;
import org.example.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import org.example.msavaliadorcredito.domain.model.SituacaoCliente;
import org.example.msavaliadorcredito.infra.clients.CartoesResourceClient;
import org.example.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
  private final ClienteResourceClient clientesClient;
  private final CartoesResourceClient cartoesClient;

  public SituacaoCliente obterSituacaoCliente(String cpf)
          throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
    try {
      // obterDadosClientes (msclientes)
      ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
      ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

      // obter cartoes do cliente (mscartoes)
      return SituacaoCliente.builder()
              .cliente(dadosClienteResponse.getBody())
              .cartoes(cartoesResponse.getBody())
              .build();
    } catch (FeignException.FeignClientException e) {
      int status = e.status();
      if (HttpStatus.NOT_FOUND.value() == status) {
        throw new DadosClienteNotFoundException();
      }
      throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
    }
  }

  public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
    throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
    try {
      ResponseEntity<DadosCliente> dadosCliente = clientesClient.dadosCliente(cpf);
      return null;
    } catch (FeignException.FeignClientException e) {
      int status = e.status();
      if (HttpStatus.NOT_FOUND.value() == status) {
        throw new DadosClienteNotFoundException();
      }
      throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
    }
  }
}