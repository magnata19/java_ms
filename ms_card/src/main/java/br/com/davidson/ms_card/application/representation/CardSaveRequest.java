package br.com.davidson.ms_card.application.representation;

import br.com.davidson.ms_card.domain.BandeiraCartao;
import br.com.davidson.ms_card.domain.Card;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Card toDomain(){
        return new Card(
                nome,
                bandeira,
                renda,
                limiteBasico
        );
    }
}
