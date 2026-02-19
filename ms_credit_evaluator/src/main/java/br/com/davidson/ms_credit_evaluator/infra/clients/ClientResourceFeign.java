package br.com.davidson.ms_credit_evaluator.infra.clients;

import br.com.davidson.ms_credit_evaluator.resources.representation.ClientData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ms-clients", path = "/clients")
public interface ClientResourceFeign {

    @GetMapping(params = "cpf")
    ResponseEntity<ClientData> getClientById(@RequestParam("cpf") String cpf);
}
