package br.com.davidson.ms_card.application;

import br.com.davidson.ms_card.application.representation.CardSaveRequest;
import br.com.davidson.ms_card.domain.Card;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardResource {

    private final CardService cardService;

    public CardResource(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping()
    public String status(){
        return "Serviço de cartões funcionando.";
    }

    @PostMapping
    public ResponseEntity<Card> save(@RequestBody CardSaveRequest request){
        Card save = cardService.save(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Card>> getCardPorRenda(@RequestParam("renda") Long renda){
        List<Card> list = cardService.pegarCartoesComRendaMenorQue(renda);
        return ResponseEntity.ok(list);
    }
}
