package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.resources.representation.ClientSituation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("evaluation")
public class CreditEvaluationController {

    @GetMapping
    public String status(){
        return "ok do serviço de avaliação de crédito";
    }

    @GetMapping(value = "client-situation", params = "cpf")
    public ResponseEntity<ClientSituation> consultClientSituation(@RequestParam("cpf") String cpf){
        return null;
    }
}
