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

}
