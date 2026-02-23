package br.com.davidson.ms_credit_evaluator.infra.events;

import br.com.davidson.ms_credit_evaluator.resources.representation.RequestIssueCardData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestIssueCardPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueIssueCards;

    public RequestIssueCardPublisher( RabbitTemplate rabbitTemplate, Queue queueIssueCards) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueIssueCards = queueIssueCards;
    }

    public void requestCard(RequestIssueCardData cardData) throws JsonProcessingException{
        String json = convertIntoJson(cardData);
        rabbitTemplate.convertAndSend(queueIssueCards.getName(), json);
    }

    private String convertIntoJson(RequestIssueCardData cardData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cardData);
    }
}
