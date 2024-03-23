package org.example.mscartoes.application;

import lombok.RequiredArgsConstructor;
import org.example.mscartoes.domain.ClienteCartao;
import org.example.mscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private ClienteCartaoRepository repository;

    public Optional<List<ClienteCartao>> listCartoesByCpf(String cpf) throws Exception {
        Optional<List<ClienteCartao>> result = repository.findClienteCartaoByCpf(cpf);
        if (result.isEmpty()) {
            throw new Exception(String.format("Cliente com cpf %s não possui cartao cadastrado", cpf));
        }
        return result;
    }
}
