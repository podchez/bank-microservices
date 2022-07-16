package com.podchez.depositservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillRequestDto {
    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Boolean overdraftEnabled;
}
