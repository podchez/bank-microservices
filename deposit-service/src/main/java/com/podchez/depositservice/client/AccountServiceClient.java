package com.podchez.depositservice.client;

import com.podchez.depositservice.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    AccountResponseDto findById(@PathVariable("id") Long id);
}
