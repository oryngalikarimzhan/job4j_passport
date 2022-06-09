package ru.job4j.client.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.job4j.client.domain.Passport;
import ru.job4j.client.service.EmailingService;

@Component
public class ExpiredPassportsController {

    private final EmailingService emailService;

    public ExpiredPassportsController(EmailingService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "unavailable")
    public void getUnavailable(ConsumerRecord<Integer, Passport> input) {
        emailService.notify(input.value());
    }
}
