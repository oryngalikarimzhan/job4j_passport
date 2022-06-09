package ru.job4j.passport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.passport.controller.PassportsController;
import ru.job4j.passport.controller.tools.PassportMapper;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.repository.PassportRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class PassportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassportsController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PassportRepository repository;

    public PassportService(ObjectMapper objectMapper, PassportRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    public Passport savePassport(Passport passport) {
        if (this.repository.findBySeriesAndNumber(passport.getSeries(), passport.getNumber())
                .isPresent()) {
            throw new IllegalArgumentException("Passport data with these series and number already exists");
        }
        return this.repository.save(passport);
    }

    public Passport updatePassport(Passport passport) {
        Passport actualPassport = this.repository.findBySeriesAndNumber(passport.getSeries(), passport.getNumber())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passport data with these series and number not exists"));
        PassportMapper.INSTANCE.update(passport, actualPassport);
        return this.repository.save(actualPassport);
    }

    public Passport deletePassport(String series, String number) {
        Passport actualPassport = this.repository.findBySeriesAndNumber(series, number)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passport data with these series and number not exists"));
        this.repository.delete(actualPassport);
        return actualPassport;
    }

    public List<Passport> findAllPassports() {
        return (List<Passport>) this.repository.findAll();
    }

    public List<Passport> findPassportsBySeries(String series) {
        return this.repository.findBySeries(series);
    }

    public Passport findPassport(String series, String number) {
        return this.repository.findBySeriesAndNumber(series, number)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Passport data with these series and number not exists"));
    }

    public List<Passport> findUnavailablePassports() {
        return this.repository.findAllByExpirationDateBefore(
                new Timestamp(System.currentTimeMillis()));
    }

    public List<Passport> findReplaceablePassports() {
        return this.repository.findAllByExpirationDateBetween(
                new Timestamp(System.currentTimeMillis()), Timestamp.valueOf(LocalDateTime.now().plusMonths(3)));
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
