package org.example.mscartoes.application;

import lombok.RequiredArgsConstructor;
import org.example.mscartoes.domain.Cartao;
import org.example.mscartoes.infra.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao request) {
        return repository.save(request);
    }

    public List<Cartao> getCartoesRendaMenorOuIgual(Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
