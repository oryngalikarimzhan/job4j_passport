package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.service.PassportService;

import java.util.List;

@RestController
public class PassportsController {
    private final PassportService service;

    public PassportsController(PassportService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return new ResponseEntity<>(
                this.service.savePassport(passport),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Passport> update(@RequestBody Passport passport) {
        return new ResponseEntity<>(
                this.service.updatePassport(passport),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{series}/{number}")
    public ResponseEntity<Passport> delete(@PathVariable String series,
                                           @PathVariable String number) {
        return new ResponseEntity<>(
                this.service.deletePassport(series, number),
                HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        return new ResponseEntity<>(
                this.service.findAllPassports(),
                HttpStatus.OK);
    }

    @GetMapping("/find/{series}")
    public ResponseEntity<List<Passport>> findBySeries(@PathVariable String series) {
        return new ResponseEntity<>(
                this.service.findPassportsBySeries(series),
                HttpStatus.OK);
    }

    @GetMapping("/find/{series}/{number}")
    public ResponseEntity<Passport> findPassport(@PathVariable String series,
                                         @PathVariable String number) {
        return new ResponseEntity<>(
                this.service.findPassport(series, number),
                HttpStatus.OK);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findUnavailable() {
        return new ResponseEntity<>(
                this.service.findUnavailablePassports(),
                HttpStatus.OK);
    }

    @GetMapping("/replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        return new ResponseEntity<>(
                this.service.findReplaceablePassports(),
                HttpStatus.OK);
    }
}