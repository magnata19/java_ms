package br.com.davidson.ms_card.application.representation;

import br.com.davidson.ms_card.domain.CardClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardClientResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public CardClientResponse fromModel(CardClient model){
        return new CardClientResponse(
                model.getCard().getNome(),
                model.getCard().getBandeira().toString(),
                model.getLimite()
        );
    }
}
