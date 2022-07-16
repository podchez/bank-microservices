package com.podchez.accountservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequestDto {

    private String fullName;

    private String email;

    private String phone;

    private List<Long> bills;
}
