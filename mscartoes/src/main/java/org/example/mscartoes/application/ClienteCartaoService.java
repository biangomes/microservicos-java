package org.example.mscartoes.application;

import lombok.RequiredArgsConstructor;
import org.example.mscartoes.domain.ClienteCartao;
import org.example.mscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return repository.findClienteCartaoByCpf(cpf);
    }
}
