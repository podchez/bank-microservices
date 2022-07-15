package com.podchez.billservice.service;

import com.podchez.billservice.exception.BillNotFoundException;
import com.podchez.billservice.model.Bill;
import com.podchez.billservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));
    }

    public Long save(Bill bill) {
        bill.setCreatedAt(OffsetDateTime.now());
        return billRepository.save(bill).getId();
    }

    public Bill update(Long id, Bill bill) {
        if (!billRepository.existsById(id)) {
            throw new BillNotFoundException("There is no bill with ID " + id + " in the DB");
        }
        bill.setId(id);
        return billRepository.save(bill);
    }

    public Bill delete(Long id) {
        Bill deletedBill = billRepository.findById(id)
                .orElseThrow(() -> new BillNotFoundException("There is no bill with ID " + id + " in the DB"));

        billRepository.deleteById(id);
        return deletedBill;
    }
}
