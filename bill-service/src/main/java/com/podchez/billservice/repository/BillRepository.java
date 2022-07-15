package com.podchez.billservice.repository;

import com.podchez.billservice.model.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {
}
