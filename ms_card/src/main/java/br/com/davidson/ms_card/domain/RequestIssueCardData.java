package br.com.davidson.ms_card.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestIssueCardData {

    private Long idCard;
    private String cpf;
    private String address;
    private BigDecimal limitLiberated;
}