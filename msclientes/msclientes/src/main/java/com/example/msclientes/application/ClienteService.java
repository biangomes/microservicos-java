package com.example.msclientes.application;

import com.example.msclientes.domain.Cliente;
import com.example.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repo;       // final pois eh uma dependencia obrigatoria

    @Transactional
    public Cliente save(Cliente cliente) {
        return repo.save(cliente);
    }

    public Optional<Cliente> getByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }

    public List<Cliente> getAll() {
        return repo.findAll();
    }

    public void removeClienteById(Long id) {
        var cliente = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Não existe cliente com ID " + id));
        repo.delete(cliente);
    }
}
