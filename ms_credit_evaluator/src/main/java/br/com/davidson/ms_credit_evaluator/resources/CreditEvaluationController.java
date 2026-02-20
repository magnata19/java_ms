package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.resources.exception.ClientDataNotFoundException;
import br.com.davidson.ms_credit_evaluator.resources.exception.MicroserviceComunicationErrorException;
import br.com.davidson.ms_credit_evaluator.resources.representation.ClientSituation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("evaluation")
public class CreditEvaluationController {

    private final CreditEvaluationService creditEvaluationService;

    public CreditEvaluationController(CreditEvaluationService creditEvaluationService) {
        this.creditEvaluationService = creditEvaluationService;
    }

    @GetMapping
    public String status(){
        return "ok do serviço de avaliação de crédito";
    }

    @GetMapping(value = "client-situation", params = "cpf")
    public ResponseEntity consultClientSituation(@RequestParam("cpf") String cpf) {
        try {
            ClientSituation clientSituation = creditEvaluationService.getClientSituation(cpf);
            return ResponseEntity.ok(clientSituation);
        } catch (ClientDataNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceComunicationErrorException ex ){
            return ResponseEntity.status(HttpStatus.resolve(ex.getStatus())).body(ex.getMessage());
        }
    }
}
