package br.com.davidson.ms_card.infra.events;

import br.com.davidson.ms_card.domain.Card;
import br.com.davidson.ms_card.domain.CardClient;
import br.com.davidson.ms_card.domain.RequestIssueCardData;
import br.com.davidson.ms_card.infra.repository.CardClientRepository;
import br.com.davidson.ms_card.infra.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmissaoCartaoSubscriber {

    private final CardRepository cardRepository;
    private final CardClientRepository cardClientRepository;

    public EmissaoCartaoSubscriber(CardRepository cardRepository, CardClientRepository cardClientRepository) {
        this.cardRepository = cardRepository;
        this.cardClientRepository = cardClientRepository;
    }

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();

            RequestIssueCardData requestIssueCardData = mapper.readValue(payload, RequestIssueCardData.class);
            Card card = cardRepository.findById(requestIssueCardData.getIdCard()).orElseThrow();

            CardClient cardClient = new CardClient();
            cardClient.setCard(card);
            cardClient.setCpf(requestIssueCardData.getCpf());
            cardClient.setLimite(requestIssueCardData.getLimitLiberated());
            cardClientRepository.save(cardClient);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
