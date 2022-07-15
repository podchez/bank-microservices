package com.podchez.billservice.controller;

import com.podchez.billservice.dto.BillRequestDto;
import com.podchez.billservice.dto.BillResponseDto;
import com.podchez.billservice.model.Bill;
import com.podchez.billservice.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController{

    private final BillService billService;

    private final ModelMapper mapper;

    @Autowired
    public BillController(BillService billService, ModelMapper mapper) {
        this.billService = billService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public BillResponseDto findById(@PathVariable Long id) {
        return convertToResponseDto(billService.findById(id));
    }

    @PostMapping
    public Long save(@RequestBody BillRequestDto billRequestDto) {
        return billService.save(convertToEntity(billRequestDto));
    }

    @PutMapping("/{id}")
    public BillResponseDto update(@PathVariable Long id, @RequestBody BillRequestDto billRequestDto) {
        return convertToResponseDto(billService.update(id, convertToEntity(billRequestDto)));
    }

    @DeleteMapping("/{id}")
    public BillResponseDto delete(@PathVariable Long id) {
        return convertToResponseDto(billService.delete(id));
    }

    // Helper methods for mapping between DTO and Entity:

    private BillResponseDto convertToResponseDto(Bill bill) {
        return mapper.map(bill, BillResponseDto.class);
    }

    private Bill convertToEntity(BillRequestDto billRequestDto) {
        return mapper.map(billRequestDto, Bill.class);
    }
}
