package br.com.davidson.ms_credit_evaluator.infra.clients;

import br.com.davidson.ms_credit_evaluator.resources.representation.Card;
import br.com.davidson.ms_credit_evaluator.resources.representation.CardClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ms-cards", path = "/cards")
public interface CardResourceFeign {
    @GetMapping(params = "cpf")
    ResponseEntity<List<CardClient>> listCardByCpf(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    public ResponseEntity<List<Card>> getCardPorRenda(@RequestParam("renda") Long renda);
}
