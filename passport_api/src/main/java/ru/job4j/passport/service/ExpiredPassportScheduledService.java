package ru.job4j.passport.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.passport.domain.Passport;
import java.util.List;

@Service
public class ExpiredPassportScheduledService {

    private final PassportService service;
    private final KafkaTemplate<Integer, Passport> kafkaTemplate;

    public ExpiredPassportScheduledService(PassportService service, KafkaTemplate<Integer, Passport> kafkaTemplate) {
        this.service = service;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 30000)
    public void send() {
        List<Passport> passports = service.findUnavailablePassports();
        if (!passports.isEmpty()) {
            passports.forEach(passport -> this.kafkaTemplate.send("unavailable", passport));
        }
    }
}
