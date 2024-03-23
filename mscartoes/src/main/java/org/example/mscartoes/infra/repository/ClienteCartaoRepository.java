package org.example.mscartoes.infra.repository;

import org.example.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
    List<ClienteCartao> findClienteCartaoByCpf(String cpf);
}
