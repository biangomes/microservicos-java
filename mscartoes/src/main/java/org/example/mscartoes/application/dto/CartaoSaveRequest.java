package org.example.mscartoes.application.dto;

import lombok.Data;
import org.example.mscartoes.domain.BandeiraCartao;
import org.example.mscartoes.domain.Cartao;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartaoSaveRequest implements Serializable {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
