package com.podchez.depositservice.controller;

import com.podchez.depositservice.dto.DepositRequestDto;
import com.podchez.depositservice.dto.DepositResponseDto;
import com.podchez.depositservice.service.DepositService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/")
    public DepositResponseDto makeDeposit(@RequestBody DepositRequestDto depositRequestDto) {
        return depositService.makeDeposit(depositRequestDto);
    }
}
