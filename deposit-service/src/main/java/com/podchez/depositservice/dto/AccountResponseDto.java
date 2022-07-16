package com.podchez.depositservice.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDto {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private OffsetDateTime createdAt;

    private List<Long> bills;
}
