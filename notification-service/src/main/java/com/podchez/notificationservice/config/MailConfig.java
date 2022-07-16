package com.podchez.notificationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    private final Environment env;

    @Autowired
    public MailConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smpt.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(env.getProperty("mail.username"));
        mailSender.setPassword(env.getProperty("mail.password"));

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", env.getProperty("mail.debug"));


        return mailSender;
    }
}
