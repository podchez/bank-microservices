package com.podchez.depositservice.repository;

import com.podchez.depositservice.model.Deposit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
    List<Deposit> findAllByEmail(String email);
}
