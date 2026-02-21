package br.com.davidson.ms_credit_evaluator.resources.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AvaliationResponseClient {
    private List<AprovedCard> cartoes;
}
