package br.com.davidson.ms_card.application;

import br.com.davidson.ms_card.domain.Card;
import br.com.davidson.ms_card.infra.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> pegarCartoesComRendaMenorQue(Long renda){
        BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);
        return cardRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
