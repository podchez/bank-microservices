package com.podchez.billservice.service;

import com.podchez.billservice.exception.BillNotFoundException;
import com.podchez.billservice.model.Bill;
import com.podchez.billservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> findAllByAccountId(Long accountId) {
        return billRepository.findAllByAccountId(accountId);
    }

    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));
    }

    @Transactional
    public Long save(Bill bill) {
        bill.setCreatedAt(OffsetDateTime.now());
        return billRepository.save(bill).getId();
    }

    @Transactional
    public Bill update(Long id, Bill updatedBill) {
        Bill oldBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));

        updatedBill.setCreatedAt(oldBill.getCreatedAt());
        updatedBill.setId(id);
        return billRepository.save(updatedBill);
    }

    @Transactional
    public Bill delete(Long id) {
        Bill deletedBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));

        billRepository.deleteById(id);
        return deletedBill;
    }
}
