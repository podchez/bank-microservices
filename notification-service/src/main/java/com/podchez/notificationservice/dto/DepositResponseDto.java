package com.podchez.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DepositResponseDto {

    private BigDecimal amount;

    private String email;
}
