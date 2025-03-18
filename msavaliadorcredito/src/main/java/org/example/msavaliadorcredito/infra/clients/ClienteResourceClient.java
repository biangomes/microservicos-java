package org.example.msavaliadorcredito.infra.clients;

// Cliente do mscliente

import org.example.msavaliadorcredito.domain.model.DadosCliente;
import org.example.msavaliadorcredito.infra.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/v1/clientes")
public interface ClienteResourceClient {
  @GetMapping(params = "cpf")
  ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
