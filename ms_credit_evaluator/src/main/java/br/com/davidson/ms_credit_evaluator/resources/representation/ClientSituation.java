package br.com.davidson.ms_credit_evaluator.resources.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientSituation {

    private ClientData clientData;
    private List<CardClient> cards;
}
