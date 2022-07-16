package com.podchez.depositservice.repository;

import com.podchez.depositservice.model.Deposit;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
}
