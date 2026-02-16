package br.com.davidson.ms_card.application;

import br.com.davidson.ms_card.application.representation.CardClientResponse;
import br.com.davidson.ms_card.application.representation.CardSaveRequest;
import br.com.davidson.ms_card.domain.Card;
import br.com.davidson.ms_card.domain.CardClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
public class CardResource {

    private final CardService cardService;
    private final CardClientService cardClientService;

    public CardResource(CardService cardService, CardClientService cardClientService) {
        this.cardService = cardService;
        this.cardClientService = cardClientService;
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

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CardClientResponse>> listCardByCpf(@RequestParam("cpf") String cpf){
        List<CardClient> list = cardClientService.listCardByCpf(cpf);
        List<CardClientResponse> cardClientResponseList = list.stream().map(
                cardClient -> new CardClientResponse().fromModel(cardClient)
        ).toList();
        return ResponseEntity.ok(cardClientResponseList);
    }
}
