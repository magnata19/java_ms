package br.com.davidson.msclient.application;

import br.com.davidson.msclient.domain.Client;
import br.com.davidson.msclient.infra.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client save(Client client){
        return repository.save(client);
    }

    public Optional<Client> getByCpf(String cpf){
        Optional<Client> client = repository.findByCpf(cpf);
        if(client.isEmpty()){
            throw new IllegalArgumentException("Usuário com cpf %s não encontrado".formatted(cpf));
        }
        return client;
    }
}
