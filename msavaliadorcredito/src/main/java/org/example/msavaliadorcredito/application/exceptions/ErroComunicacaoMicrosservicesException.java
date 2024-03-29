package org.example.msavaliadorcredito.application.exceptions;

import lombok.Getter;

public class ErroComunicacaoMicrosservicesException extends Exception {
    @Getter
    private Integer status;
    public ErroComunicacaoMicrosservicesException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
