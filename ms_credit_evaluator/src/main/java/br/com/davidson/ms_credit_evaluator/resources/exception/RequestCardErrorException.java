package br.com.davidson.ms_credit_evaluator.resources.exception;

public class RequestCardErrorException extends RuntimeException {

    public RequestCardErrorException(String message) {
        super(message);
    }
}
