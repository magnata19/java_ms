package br.com.davidson.ms_credit_evaluator.resources.representation;

import lombok.Data;

import java.util.List;

@Data
public class ClientSituation {

    private ClientData clientData;
    private List<CardClient> cards;
}
