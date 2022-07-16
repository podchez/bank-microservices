package com.podchez.billservice.repository;

import com.podchez.billservice.model.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {

    List<Bill> findAllByAccountId(Long accountId);
}
