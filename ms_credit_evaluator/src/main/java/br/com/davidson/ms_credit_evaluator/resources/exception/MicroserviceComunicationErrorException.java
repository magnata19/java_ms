package br.com.davidson.ms_credit_evaluator.resources.exception;

import lombok.Getter;

public class MicroserviceComunicationErrorException extends Exception {

    @Getter
    private final Integer status;

    public MicroserviceComunicationErrorException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
