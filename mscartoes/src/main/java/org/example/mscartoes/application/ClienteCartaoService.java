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
    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf) throws Exception {
        List<ClienteCartao> result = repository.findClienteCartaoByCpf(cpf);

        return result;
    }
}
