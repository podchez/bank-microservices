package com.podchez.billservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BillResponseDto {

    private Long id;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Boolean overdraftEnabled;

    private OffsetDateTime createdAt;
}
