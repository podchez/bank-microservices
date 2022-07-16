package com.podchez.depositservice.util;

import com.podchez.depositservice.dto.AccountResponseDto;
import com.podchez.depositservice.dto.BillResponseDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class DummyCreator {

    public static AccountResponseDto createDummyAccountResponseDto() {
        return AccountResponseDto.builder()
                .id(1L)
                .fullName("Dummy Dummy")
                .email("dummy@dummy.com")
                .phone("+79998887766")
                .createdAt(OffsetDateTime.now())
                .bills(List.of(1L, 2L, 3L))
                .build();
    }

    public static BillResponseDto createDummyBillResponseDto() {
        return BillResponseDto.builder()
                .id(1L)
                .accountId(1L)
                .amount(BigDecimal.valueOf(1000L))
                .isDefault(true)
                .overdraftEnabled(true)
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
