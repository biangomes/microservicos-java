package org.example.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import org.example.msavaliadorcredito.application.exceptions.ErroComunicacaoMicrosservicesException;
import org.example.msavaliadorcredito.application.exceptions.SolicitacaoCartaoException;
import org.example.msavaliadorcredito.domain.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

  private final AvaliadorCreditoService avaliadorCreditoService;

  @GetMapping
  public String status() {
    return "ok";
  }

  @GetMapping(value = "/situacao", params = "cpf")
  public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
      try {
          SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
          return ResponseEntity.ok(situacaoCliente);
      } catch (DadosClienteNotFoundException e) {
        return ResponseEntity.notFound().build();
      } catch (ErroComunicacaoMicrosservicesException e) {
        return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
      }
  }

  @PostMapping
  public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados)
    throws DadosClienteNotFoundException, ErroComunicacaoMicrosservicesException {
      try {
          RetornoAvaliacaoCliente retornoCliente = avaliadorCreditoService
              .realizarAvaliacao(dados.getCpf(), dados.getRenda());
          return ResponseEntity.ok(retornoCliente);
      } catch (DadosClienteNotFoundException e) {
          throw new RuntimeException(e);
      } catch (ErroComunicacaoMicrosservicesException e) {
          return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
      }
  }

  @PostMapping("solicitacoes-cartao")
  public ResponseEntity solicitaCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
      try {
          var protocoloSolicitacaoCartao = avaliadorCreditoService
                  .solicitarEmissaoCartao(dados);
          return ResponseEntity.ok(protocoloSolicitacaoCartao);
      } catch (SolicitacaoCartaoException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
      }
  }
}
