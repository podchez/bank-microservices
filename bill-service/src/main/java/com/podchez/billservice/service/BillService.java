package com.podchez.billservice.service;

import com.podchez.billservice.exception.BillNotFoundException;
import com.podchez.billservice.model.Bill;
import com.podchez.billservice.repository.BillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> findAllByAccountId(Long accountId) {
        log.info("IN findAllByAccountId - accountId: {}", accountId);
        return billRepository.findAllByAccountId(accountId);
    }

    public Bill findById(Long id) {
        log.info("IN findById - id: {}", id);
        return billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));
    }

    @Transactional
    public Long save(Bill bill) {
        log.info("IN save - bill's amount: {}", bill.getAmount());
        bill.setCreatedAt(OffsetDateTime.now());
        return billRepository.save(bill).getId();
    }

    @Transactional
    public Bill update(Long id, Bill updatedBill) {
        log.info("IN update - id: {}, bill's amount: {}", id, updatedBill.getAmount());
        Bill oldBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));

        updatedBill.setCreatedAt(oldBill.getCreatedAt());
        updatedBill.setId(id);
        return billRepository.save(updatedBill);
    }

    @Transactional
    public Bill delete(Long id) {
        log.info("IN delete - id: {}", id);
        Bill deletedBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));

        billRepository.deleteById(id);
        log.info("IN delete - bill with id: {} successfully deleted", id);
        return deletedBill;
    }
}
