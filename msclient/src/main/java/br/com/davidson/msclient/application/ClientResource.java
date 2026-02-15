package br.com.davidson.msclient.application;

import br.com.davidson.msclient.application.representation.ClientCreateRequest;
import br.com.davidson.msclient.domain.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("clients")
@Slf4j
public class ClientResource {

    private final ClientService service;

    public ClientResource(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microserviço de clientes");
        return "ok";
    }

    @PostMapping()
    public ResponseEntity save(@RequestBody ClientCreateRequest dto) {
        Client client = service.save(dto.toModel());
        URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(client.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity getClientById(@RequestParam String cpf){
        Optional<Client> client = service.getByCpf(cpf);
        if(client.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

}
