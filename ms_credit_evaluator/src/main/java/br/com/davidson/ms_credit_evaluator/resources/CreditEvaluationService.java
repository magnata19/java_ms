package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.infra.clients.CardResourceFeign;
import br.com.davidson.ms_credit_evaluator.infra.clients.ClientResourceFeign;
import br.com.davidson.ms_credit_evaluator.resources.exception.ClientDataNotFoundException;
import br.com.davidson.ms_credit_evaluator.resources.exception.MicroserviceComunicationErrorException;
import br.com.davidson.ms_credit_evaluator.resources.representation.*;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditEvaluationService {

    private final ClientResourceFeign clientResourceFeign;
    private final CardResourceFeign cardResourceFeign;

    public CreditEvaluationService(ClientResourceFeign clientResourceFeign, CardResourceFeign cardResourceFeign) {
        this.clientResourceFeign = clientResourceFeign;
        this.cardResourceFeign = cardResourceFeign;
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
            ResponseEntity<ClientData> client = clientResourceFeign.getClientById(cpf);
            ResponseEntity<List<Card>> cardsResponse = cardResourceFeign.getCardPorRenda(renda);
            List<Card> cards = cardsResponse.getBody();

            List<AprovedCard> cardsAproved = cards.stream().map(card -> {

                ClientData clientBody = client.getBody();
                BigDecimal basicLimit = card.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(clientBody.getIdade());
                BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal aprovedLimit = fator.multiply(basicLimit);

                AprovedCard aprovedCard = new AprovedCard();
                aprovedCard.setCartao(card.getNome());
                aprovedCard.setBandeira(card.getBandeira());
                aprovedCard.setLimiteAprovado(aprovedLimit);
                return aprovedCard;
            }).toList();

            return new AvaliationResponseClient(cardsAproved);

        } catch (FeignException.FeignClientException ex) {
            int status = ex.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientDataNotFoundException();
            }
            throw new MicroserviceComunicationErrorException(ex.getMessage(), status);
        }
    }
}
