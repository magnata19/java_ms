package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.infra.clients.CardResourceFeign;
import br.com.davidson.ms_credit_evaluator.infra.clients.ClientResourceFeign;
import br.com.davidson.ms_credit_evaluator.infra.events.RequestIssueCardPublisher;
import br.com.davidson.ms_credit_evaluator.resources.exception.ClientDataNotFoundException;
import br.com.davidson.ms_credit_evaluator.resources.exception.MicroserviceComunicationErrorException;
import br.com.davidson.ms_credit_evaluator.resources.exception.RequestCardErrorException;
import br.com.davidson.ms_credit_evaluator.resources.representation.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CreditEvaluationService {

    private final ClientResourceFeign clientResourceFeign;
    private final CardResourceFeign cardResourceFeign;
    private final RequestIssueCardPublisher requestIssueCardPublisher;

    public CreditEvaluationService(ClientResourceFeign clientResourceFeign, CardResourceFeign cardResourceFeign,
                                   RequestIssueCardPublisher requestIssueCardPublisher) {
        this.clientResourceFeign = clientResourceFeign;
        this.cardResourceFeign = cardResourceFeign;
        this.requestIssueCardPublisher = requestIssueCardPublisher;
    }

    public ClientSituation getClientSituation(String cpf) throws ClientDataNotFoundException,
            MicroserviceComunicationErrorException {
        try {
            ResponseEntity<ClientData> client = clientResourceFeign.getClientById(cpf);
            ResponseEntity<List<CardClient>> cards = cardResourceFeign.listCardByCpf(cpf);
            return ClientSituation
                    .builder()
                    .clientData(client.getBody())
                    .cards(cards.getBody())
                    .build();
        } catch (FeignException.FeignClientException ex) {
            int status = ex.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientDataNotFoundException();
            }
            throw new MicroserviceComunicationErrorException(ex.getMessage(), status);
        }
    }

    public AvaliationResponseClient makeAvaliation(String cpf, Long renda) throws ClientDataNotFoundException,
            MicroserviceComunicationErrorException  {
        try {
            ResponseEntity<ClientData> client = clientResourceFeign.getClientById(cpf); // 1. Buscar os dados do cliente, para pegar a idade
            ResponseEntity<List<Card>> cardsResponse = cardResourceFeign.getCardPorRenda(renda); // 2. Buscar os cartões que o cliente pode solicitar, de acordo com a renda
            List<Card> cards = cardsResponse.getBody();

            var cardsApproved = cards.stream().map(card -> {
                ClientData clientBody = client.getBody();

                BigDecimal basicLimit = card.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(clientBody.getIdade());// 30 anos = 30
                BigDecimal factor = idadeBD.divide(BigDecimal.valueOf(10));// 30 = 3
                BigDecimal approvedLimit = factor.multiply(basicLimit); // 3 * 1000 = 3000

                AprovedCard aprovedCard = new AprovedCard();
                aprovedCard.setCartao(card.getNome());
                aprovedCard.setBandeira(card.getBandeira());
                aprovedCard.setLimiteAprovado(approvedLimit); // 3000

                return aprovedCard;
            }).toList();

            return new AvaliationResponseClient(cardsApproved);

        } catch (FeignException.FeignClientException ex) {
            int status = ex.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientDataNotFoundException();
            }
            throw new MicroserviceComunicationErrorException(ex.getMessage(), status);
        }
    }

    public RequestProtocolCard requestIssueCard(RequestIssueCardData data) {
        try {
            requestIssueCardPublisher.requestCard(data);
            var protocol = UUID.randomUUID().toString();
            return new RequestProtocolCard(protocol);
        } catch (Exception e){
            throw new RequestCardErrorException(e.getMessage());
        }
    }
}
