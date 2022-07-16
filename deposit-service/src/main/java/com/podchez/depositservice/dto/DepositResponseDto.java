package com.podchez.depositservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepositResponseDto {

    private BigDecimal amount;

    private String email;
}
