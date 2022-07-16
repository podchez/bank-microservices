package com.podchez.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podchez.notificationservice.config.RabbitMQConfig;
import com.podchez.notificationservice.dto.DepositResponseDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);

        String jsonBody = new String(message.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);

        System.out.println(depositResponseDto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDto.getEmail());
        mailMessage.setFrom("testbankmicroservices@gmail.com");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Made deposit with sum: " + depositResponseDto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
