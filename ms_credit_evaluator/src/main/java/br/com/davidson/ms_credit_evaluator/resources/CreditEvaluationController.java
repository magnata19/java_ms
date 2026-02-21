package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.resources.exception.ClientDataNotFoundException;
import br.com.davidson.ms_credit_evaluator.resources.exception.MicroserviceComunicationErrorException;
import br.com.davidson.ms_credit_evaluator.resources.representation.AvaliationData;
import br.com.davidson.ms_credit_evaluator.resources.representation.AvaliationResponseClient;
import br.com.davidson.ms_credit_evaluator.resources.representation.ClientSituation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> consultClientSituation(@RequestParam("cpf") String cpf) {
        try {
            ClientSituation clientSituation = creditEvaluationService.getClientSituation(cpf);
            return ResponseEntity.ok(clientSituation);
        } catch (ClientDataNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceComunicationErrorException ex ){
            return ResponseEntity.status(HttpStatus.resolve(ex.getStatus())).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> makeAvaliation(@RequestBody AvaliationData data){
        try {
            AvaliationResponseClient avaliationResponseClient = creditEvaluationService.makeAvaliation(data.getCpf(), data.getRenda());
            return ResponseEntity.ok(avaliationResponseClient);
        } catch (ClientDataNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceComunicationErrorException ex ){
            return ResponseEntity.status(HttpStatus.resolve(ex.getStatus())).body(ex.getMessage());
        }
    }
}
