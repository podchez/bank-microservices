package com.podchez.accountservice.repository;

import com.podchez.accountservice.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
