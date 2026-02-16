package br.com.davidson.ms_card.infra.repository;

import br.com.davidson.ms_card.domain.CardClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardClientRepository extends JpaRepository<CardClient, Long> {
    List<CardClient> findByCpf(String cpf);
}
