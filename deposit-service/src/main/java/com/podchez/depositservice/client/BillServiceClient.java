package com.podchez.depositservice.client;

import com.podchez.depositservice.dto.BillRequestDto;
import com.podchez.depositservice.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @RequestMapping(value = "/bills/{id}", method = RequestMethod.GET)
    BillResponseDto findById(@PathVariable("id") Long id);

    @RequestMapping(value = "/bills/{id}", method = RequestMethod.PUT)
    void update(@PathVariable("id") Long id, BillRequestDto billRequestDto);

    @RequestMapping(value = "/bills/account/{accountId}", method = RequestMethod.GET)
    List<BillResponseDto> findAllByAccountId(@PathVariable("accountId") Long accountId);
}
