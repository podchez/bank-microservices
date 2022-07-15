package com.podchez.accountservice.controller;

import com.podchez.accountservice.dto.AccountRequestDto;
import com.podchez.accountservice.dto.AccountResponseDto;
import com.podchez.accountservice.model.Account;
import com.podchez.accountservice.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper mapper;

    @Autowired
    public AccountController(AccountService accountService, ModelMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public AccountResponseDto findById(@PathVariable Long id) {
        return convertToResponseDto(accountService.findById(id));
    }

    @PostMapping
    public Long save(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.save(convertToEntity(accountRequestDto));
    }

    @PutMapping("/{id}")
    public AccountResponseDto update(@PathVariable Long id, @RequestBody AccountRequestDto accountRequestDto) {
        return convertToResponseDto(accountService.update(id, convertToEntity(accountRequestDto)));
    }

    @DeleteMapping("/{id}")
    public AccountResponseDto delete(@PathVariable Long id) {
        return convertToResponseDto(accountService.delete(id));
    }

    // Helper methods for mapping between DTO and Entity:

    private AccountResponseDto convertToResponseDto(Account account) {
        return mapper.map(account, AccountResponseDto.class);
    }

    private Account convertToEntity(AccountRequestDto accountRequestDto) {
        return mapper.map(accountRequestDto, Account.class);
    }
}
