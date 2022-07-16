package com.podchez.depositservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podchez.depositservice.client.AccountServiceClient;
import com.podchez.depositservice.client.BillServiceClient;
import com.podchez.depositservice.dto.*;
import com.podchez.depositservice.exception.DepositServiceException;
import com.podchez.depositservice.model.Deposit;
import com.podchez.depositservice.repository.DepositRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    private final static String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private final static String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository, AccountServiceClient accountServiceClient,
                          BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDto makeDeposit(DepositRequestDto depositRequestDto) {
        Long accountId = depositRequestDto.getAccountId();
        Long billId = depositRequestDto.getBillId();
        BigDecimal amount = depositRequestDto.getAmount();

        if (accountId == null && billId == null) {
            throw new DepositServiceException("accountId is null and billId is null");
        }

        if (billId != null) {
            BillResponseDto billResponseDto = billServiceClient.findById(billId);
            BillRequestDto billRequestDto = createBillRequestDtoWithUpdatedAmount(amount, billResponseDto);

            billServiceClient.update(billId, billRequestDto);

            AccountResponseDto accountResponseDto = accountServiceClient.findById(billResponseDto.getAccountId());
            depositRepository.save(Deposit.builder()
                    .amount(amount)
                    .billId(billId)
                    .email(accountResponseDto.getEmail())
                    .createdAt(OffsetDateTime.now())
                    .build());

            return createResponseDtoAndSendToRabbitMQ(amount, accountResponseDto);
        } else {
            // billId == null, taking default account's bill
            BillResponseDto defaultBill = getDefaultBill(accountId);
            BillRequestDto billRequestDto = createBillRequestDtoWithUpdatedAmount(amount, defaultBill);

            billServiceClient.update(defaultBill.getId(), billRequestDto);

            AccountResponseDto accountResponseDto = accountServiceClient.findById(accountId);
            depositRepository.save(Deposit.builder()
                    .amount(amount)
                    .billId(defaultBill.getId())
                    .email(accountResponseDto.getEmail())
                    .createdAt(OffsetDateTime.now())
                    .build());

            return createResponseDtoAndSendToRabbitMQ(amount, accountResponseDto);
        }
    }

    // Helper methods:

    private DepositResponseDto createResponseDtoAndSendToRabbitMQ(BigDecimal amount, AccountResponseDto accountResponseDto) {
        DepositResponseDto depositResponseDto = new DepositResponseDto(amount, accountResponseDto.getEmail());

        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    new ObjectMapper().writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Cannot send message to RabbitMQ");
        }
        return depositResponseDto;
    }

    private BillRequestDto createBillRequestDtoWithUpdatedAmount(BigDecimal amount, BillResponseDto billResponseDto) {
        return BillRequestDto.builder()
                .accountId(billResponseDto.getAccountId())
                .isDefault(billResponseDto.getIsDefault())
                .overdraftEnabled(billResponseDto.getOverdraftEnabled())
                .amount(billResponseDto.getAmount().add(amount)) // adding deposit's amount to bill
                .build();
    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.findAllByAccountId(accountId).stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Cannot find default bill for account with ID: " + accountId));
    }
}
