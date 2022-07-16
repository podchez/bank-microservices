package com.podchez.depositservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podchez.depositservice.DepositServiceApplication;
import com.podchez.depositservice.client.AccountServiceClient;
import com.podchez.depositservice.client.BillServiceClient;
import com.podchez.depositservice.config.H2DatabaseConfig;
import com.podchez.depositservice.dto.AccountResponseDto;
import com.podchez.depositservice.dto.BillResponseDto;
import com.podchez.depositservice.dto.DepositResponseDto;
import com.podchez.depositservice.model.Deposit;
import com.podchez.depositservice.repository.DepositRepository;
import com.podchez.depositservice.util.DummyCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        DepositServiceApplication.class,
        H2DatabaseConfig.class
})
public class DepositControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepositRepository depositRepository;

    @MockBean
    private BillServiceClient billServiceClient;

    @MockBean
    private AccountServiceClient accountServiceClient;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private static final String REQUEST = "{\n" +
            "    \"billId\": 1,\n" +
            "    \"amount\": 1000\n" +
            "}";

    @Test
    public void shouldMakeDeposit_when() throws Exception {
        // given
        BillResponseDto billResponseDto = DummyCreator.createDummyBillResponseDto();
        Mockito.when(billServiceClient.findById(ArgumentMatchers.anyLong()))
                .thenReturn(billResponseDto);

        AccountResponseDto accountResponseDto = DummyCreator.createDummyAccountResponseDto();
        Mockito.when(accountServiceClient.findById(ArgumentMatchers.anyLong()))
                .thenReturn(accountResponseDto);

        // when
        String responseBody = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        List<Deposit> deposits = depositRepository.findAllByEmail(accountResponseDto.getEmail());
        DepositResponseDto actual = new ObjectMapper().readValue(responseBody, DepositResponseDto.class);

        // then
        assertThat(actual.getEmail())
                .isEqualTo(deposits.get(0).getEmail());
        assertThat(actual.getAmount())
                .isEqualTo(BigDecimal.valueOf(1000));
    }
}
