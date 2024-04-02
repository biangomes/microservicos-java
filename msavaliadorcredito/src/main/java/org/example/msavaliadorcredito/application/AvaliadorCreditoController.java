package org.example.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.application.exceptions.DadosClienteNotFoundException;
import org.example.msavaliadorcredito.application.exceptions.ErroComunicacaoMicrosservicesException;
import org.example.msavaliadorcredito.domain.model.DadosAvaliacao;
import org.example.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import org.example.msavaliadorcredito.domain.model.SituacaoCliente;
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
      SituacaoCliente situacaoCliente = null;
      try {
          situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
      } catch (DadosClienteNotFoundException e) {
          throw new RuntimeException(e);
      } catch (ErroComunicacaoMicrosservicesException e) {
          return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
      }
      return ResponseEntity.ok(situacaoCliente);
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
}
