package ru.job4j.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.client.domain.EmailMessage;
import ru.job4j.client.domain.Passport;
@Service
public class EmailingService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailingService.class);
    @Value("${email.service.sender}")
    private String sender;
    @Value("${email.service.expired.passport.text}")
    private String text;

    public void notify(Passport passport) {
        EmailMessage message = new EmailMessage(sender, passport.getEmail(), text, passport);
        LOG.info(String.format("message sent to %s", passport.getEmail()));
    }
}
