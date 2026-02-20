package br.com.davidson.ms_credit_evaluator.resources;

import br.com.davidson.ms_credit_evaluator.infra.clients.CardResourceFeign;
import br.com.davidson.ms_credit_evaluator.infra.clients.ClientResourceFeign;
import br.com.davidson.ms_credit_evaluator.resources.exception.ClientDataNotFoundException;
import br.com.davidson.ms_credit_evaluator.resources.exception.MicroserviceComunicationErrorException;
import br.com.davidson.ms_credit_evaluator.resources.representation.CardClient;
import br.com.davidson.ms_credit_evaluator.resources.representation.ClientData;
import br.com.davidson.ms_credit_evaluator.resources.representation.ClientSituation;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
