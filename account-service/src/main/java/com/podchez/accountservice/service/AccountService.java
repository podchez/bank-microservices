package com.podchez.accountservice.service;

import com.podchez.accountservice.exception.AccountNotFoundException;
import com.podchez.accountservice.model.Account;
import com.podchez.accountservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findById(Long id) {
        log.info("IN findById - id: {}", id);
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("There is no account with ID " + id + " in the DB"));
    }

    @Transactional
    public Long save(Account account) {
        log.info("IN save - account's fullName: {}", account.getFullName());
        account.setCreatedAt(OffsetDateTime.now());
        return accountRepository.save(account).getId();
    }

    @Transactional
    public Account update(Long id, Account updatedAccount) {
        log.info("IN update - id: {}, account's fullName: {}", id, updatedAccount.getFullName());
        Account oldAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("There is no account with ID " + id + " in the DB"));

        updatedAccount.setCreatedAt(oldAccount.getCreatedAt());
        updatedAccount.setId(id);
        return accountRepository.save(updatedAccount);
    }

    @Transactional
    public Account delete(Long id) {
        log.info("IN delete - id: {}", id);
        Account deletedAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("There is no account with ID " + id + " in the DB"));

        accountRepository.deleteById(id);
        log.info("IN delete - account with id: {} successfully deleted", id);
        return deletedAccount;
    }
}
