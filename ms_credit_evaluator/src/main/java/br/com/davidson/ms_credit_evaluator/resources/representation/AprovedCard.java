package br.com.davidson.ms_credit_evaluator.resources.representation;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AprovedCard {
    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
