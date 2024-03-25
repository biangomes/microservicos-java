package org.example.mscartoes.application;

import lombok.RequiredArgsConstructor;
import org.example.mscartoes.application.dto.CartaoSaveRequest;
import org.example.mscartoes.application.dto.CartoesPorClienteResponse;
import org.example.mscartoes.domain.Cartao;
import org.example.mscartoes.domain.ClienteCartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;
    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorOuIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) throws Exception {
        // Chama o metodo do Service normalmente
        List<ClienteCartao> clienteCartaos = clienteCartaoService.listCartoesByCpf(cpf);
        // Entendo que aqui seria uma "conversao" entre o DTO e o proprio model
        List<CartoesPorClienteResponse> resultList = clienteCartaos.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
