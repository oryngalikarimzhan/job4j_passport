package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
public class PassportsController {
    private PassportRepository passports;

    public PassportsController(PassportRepository passports) {
        this.passports = passports;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return new ResponseEntity<>(
                this.passports.save(passport),
                HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<Passport> update(@RequestBody Passport passport)
            throws InvocationTargetException, IllegalAccessException {
        Passport oldPassport = this.passports.findBySerialNumber(passport.getSerialNumber())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passport is not found"));
        var methods = oldPassport.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method : methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Impossible invoke set method from object : " + oldPassport + ", Check set and get pairs.");
                }
                var newValue = getMethod.invoke(passport);
                if (newValue != null) {
                    setMethod.invoke(oldPassport, newValue);
                }
            }
        }
        return new ResponseEntity<>(
                this.passports.save(oldPassport),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{serialNumber}")
    public ResponseEntity<Passport> delete(@PathVariable String serialNumber) {
        Passport passport = this.passports.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passport is not found"));
        this.passports.delete(passport);
        return new ResponseEntity<>(passport, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        return new ResponseEntity<>((List<Passport>) this.passports.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find/{serialNumber}")
    public ResponseEntity<Passport> find(@PathVariable String serialNumber) {
        Passport passport = this.passports.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passport is not found"));
        return new ResponseEntity<>(passport, HttpStatus.OK);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findUnavailable() {
        List<Passport> unavailablePassports = this.passports.findAllByExpirationDateBefore(
                new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(unavailablePassports, HttpStatus.OK);
    }

    @GetMapping("/replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        List<Passport> unavailablePassports = this.passports.findAllByExpirationDateBetween(
                new Timestamp(System.currentTimeMillis()), Timestamp.valueOf(LocalDateTime.now().plusMonths(3)));
        return new ResponseEntity<>(unavailablePassports, HttpStatus.OK);
    }
}
