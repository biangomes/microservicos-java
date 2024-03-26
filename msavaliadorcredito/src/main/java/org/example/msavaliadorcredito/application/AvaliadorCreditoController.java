package org.example.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import org.example.msavaliadorcredito.domain.model.SituacaoCliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

  private final AvaliadorCreditoService avaliadorCreditoService;

  @GetMapping
  public String status() {
    return "ok";
  }

  @GetMapping(value = "/situacao", params = "cpf")
  public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
    SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
    return null;
  }
}
