package br.com.davidson.ms_credit_evaluator.resources.exception;

public class ClientDataNotFoundException extends Exception {
    public ClientDataNotFoundException() {
        super("Dados do cliente não encontrados para o CPF informado.");
    }
}
