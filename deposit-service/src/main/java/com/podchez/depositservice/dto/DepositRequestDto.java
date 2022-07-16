package com.podchez.depositservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DepositRequestDto {

    private Long accountId;

    private Long billId;

    private BigDecimal amount;
}
