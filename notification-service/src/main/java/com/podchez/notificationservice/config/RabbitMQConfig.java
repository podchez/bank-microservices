package com.podchez.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public final static String QUEUE_DEPOSIT = "js.deposit.notify";
    private final static String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private final static String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final AmqpAdmin amqpAdmin;

    @Autowired
    public RabbitMQConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Bean
    public TopicExchange depositExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_DEPOSIT);
    }

    @Bean
    public Queue queueDeposit() {
        return new Queue(QUEUE_DEPOSIT);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder
                .bind(queueDeposit())
                .to(depositExchange())
                .with(ROUTING_KEY_DEPOSIT);
    }
}
