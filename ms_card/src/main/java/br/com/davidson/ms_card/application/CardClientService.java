package br.com.davidson.ms_card.application;

import br.com.davidson.ms_card.domain.CardClient;
import br.com.davidson.ms_card.infra.repository.CardClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardClientService {

    private final CardClientRepository cardClientRepository;

    public CardClientService(CardClientRepository cardClientRepository) {
        this.cardClientRepository = cardClientRepository;
    }

    public List<CardClient> listCardByCpf(String cpf) {
        return  cardClientRepository.findByCpf(cpf);
    }
}
