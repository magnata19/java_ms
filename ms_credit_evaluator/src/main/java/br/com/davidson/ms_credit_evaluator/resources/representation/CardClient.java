package br.com.davidson.ms_credit_evaluator.resources.representation;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardClient {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;
}
