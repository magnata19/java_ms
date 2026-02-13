package br.com.davidson.msclient.application.representation;

import br.com.davidson.msclient.domain.Client;
import lombok.Data;

@Data
public class ClientCreateRequest {
    private String nome;
    private String cpf;
    private Integer idade;

    public Client toModel(){
        return new Client(nome, cpf, idade);
    }
}
