package com.example.msclientes.application;

import com.example.msclientes.application.representation.ClienteSaveRequest;
import com.example.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteService service;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de clientes");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder        // ServletUriComponentsBuilder := URL dinâmica
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @PostMapping("/save")
    public ResponseEntity<Cliente> saveClient(@RequestBody ClienteSaveRequest request) {
        var cliente = request.toModel();
        cliente = service.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf) {
        var cliente = service.getByCpf(cpf);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> clientes() {
        var clientes = service.getAll();
        return new ResponseEntity(clientes, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeCliente(Long id) {
        service.removeClienteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
