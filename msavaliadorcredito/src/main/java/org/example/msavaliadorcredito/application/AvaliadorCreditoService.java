package org.example.msavaliadorcredito.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import org.example.msavaliadorcredito.application.exceptions.ErroComunicacaoMicrosservicesException;
import org.example.msavaliadorcredito.application.exceptions.SolicitacaoCartaoException;
import org.example.msavaliadorcredito.domain.model.*;
import org.example.msavaliadorcredito.infra.clients.CartoesResourceClient;
import org.example.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.example.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
  @Autowired
  private final ClienteResourceClient clientesClient;
  @Autowired
  private final CartoesResourceClient cartoesClient;
  private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
  private final Logger logger = LoggerFactory.getLogger(AvaliadorCreditoService.class);

  public SituacaoCliente obterSituacaoCliente(String cpf)
          throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
    try {
      // obterDadosClientes (msclientes)
      var dadosClienteResponse = clientesClient.dadosCliente(cpf);
      var cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

      // obter cartoes do cliente (mscartoes)
      return SituacaoCliente.builder()
              .cliente(dadosClienteResponse)
              .cartoes(cartoesResponse)
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
      logger.info(String.format("Validando cliente com cpf {%s}", cpf));
      var dadosClienteResponse = clientesClient.dadosCliente(cpf);
      logger.info(String.format("CPF: {%s} / Nome: {%s}", cpf, dadosClienteResponse.getNome()));
      logger.info(String.format("Busca cartoes de renda ate: %s", renda));
      var cartoesResponse = cartoesClient.getCartoesRendaAte(renda);
      logger.info("Cartoes aptos: ", cartoesResponse.get(0));

      var cartoes = cartoesResponse;
      var listaDeCartoesAprovados = cartoes.stream().map(cartao -> {

        var dadosCliente = dadosClienteResponse;

        var limiteBasico = cartao.getLimiteBasico();
        var idadeBd = BigDecimal.valueOf(dadosCliente.getIdade());
        var fator = idadeBd.divide(BigDecimal.valueOf(10));
        var limiteAprovado = fator.multiply(limiteBasico);

        var aprovado = new CartaoAprovado();
        aprovado.setCartao(cartao.getNome());
        aprovado.setBandeira(cartao.getBandeira());
        aprovado.setLimiteAprovado(limiteAprovado);

        return aprovado;
      }).collect(Collectors.toList());

      return new RetornoAvaliacaoCliente(listaDeCartoesAprovados);
    } catch (FeignException.FeignClientException e) {
      logger.error(e.getMessage());
      var status = e.status();
      if (HttpStatus.NOT_FOUND.value() == status) {
        throw new DadosClienteNotFoundException();
      }
      throw new ErroComunicacaoMicrosservicesException(e.getMessage(), status);
    }
  }

  public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
    try {
      emissaoCartaoPublisher.solicitarCartao(dados);
      var protocolo = UUID.randomUUID().toString();
      return new ProtocoloSolicitacaoCartao(protocolo);
    } catch (Exception e) {
      throw new SolicitacaoCartaoException(e.getMessage());
    }
  }
}
