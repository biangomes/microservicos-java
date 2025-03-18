package org.example.msavaliadorcredito.infra.clients;

import org.example.msavaliadorcredito.domain.model.Cartao;
import org.example.msavaliadorcredito.domain.model.CartaoCliente;
import org.example.msavaliadorcredito.infra.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/v1/cartoes", configuration = OpenFeignConfig.class)
public interface CartoesResourceClient {
    @GetMapping(params = "cpf")
    List<CartaoCliente> getCartoesByCliente(@RequestParam("cpf") String cpf);
    @GetMapping(params = "renda", consumes = "application/json")
    List<Cartao> getCartoesRendaAte(@RequestParam("renda") Long renda);
}
