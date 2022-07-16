package com.podchez.depositservice.service;

import com.podchez.depositservice.client.AccountServiceClient;
import com.podchez.depositservice.client.BillServiceClient;
import com.podchez.depositservice.dto.AccountResponseDto;
import com.podchez.depositservice.dto.BillResponseDto;
import com.podchez.depositservice.dto.DepositRequestDto;
import com.podchez.depositservice.dto.DepositResponseDto;
import com.podchez.depositservice.exception.DepositServiceException;
import com.podchez.depositservice.repository.DepositRepository;
import com.podchez.depositservice.util.DummyCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void shouldMakeDeposit_whenBillIdExists() {
        // given
        BillResponseDto billResponseDto = DummyCreator.createDummyBillResponseDto();
        Mockito.when(billServiceClient.findById(ArgumentMatchers.anyLong()))
                .thenReturn(billResponseDto);

        AccountResponseDto accountResponseDto = DummyCreator.createDummyAccountResponseDto();
        Mockito.when(accountServiceClient.findById(ArgumentMatchers.anyLong()))
                .thenReturn(accountResponseDto);

        // when
        DepositResponseDto actual = depositService.makeDeposit(
                new DepositRequestDto(null, 1L, BigDecimal.valueOf(1000L)));

        // then
        assertThat(actual.getEmail())
                .isEqualTo(accountResponseDto.getEmail());
    }

    @Test
    public void shouldThrowException_whenMakeDepositWithIncorrectData() {
        assertThatThrownBy(() -> depositService.makeDeposit(
                new DepositRequestDto(null, null, BigDecimal.valueOf(1000L))))
                .isInstanceOf(DepositServiceException.class);
    }
}
