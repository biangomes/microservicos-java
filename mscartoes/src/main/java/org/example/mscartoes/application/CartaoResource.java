package org.example.mscartoes.application;

import lombok.RequiredArgsConstructor;
import org.example.mscartoes.application.dto.CartaoSaveRequest;
import org.example.mscartoes.domain.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoResource {

    private final CartaoService service;
    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = service.getCartoesRendaMenorOuIgual(renda);
        return ResponseEntity.ok(list);
    }
}
