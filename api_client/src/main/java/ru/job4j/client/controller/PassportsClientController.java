package ru.job4j.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.client.domain.Passport;

import java.util.List;

@RestController
public class PassportsClientController {
    private final RestTemplate rest;
    @Value("${passport.api.find}")
    private String findApi;
    @Value("${passport.api.save}")
    private String saveApi;
    @Value("${passport.api.update}")
    private String updateApi;
    @Value("${passport.api.delete}")
    private String deleteApi;
    @Value("${passport.api.unavailable}")
    private String unavailableApi;
    @Value("${passport.api.replaceable}")
    private String replaceableApi;

    public PassportsClientController(RestTemplate rest) {
        this.rest = rest;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        Passport respPassport = rest.postForObject(saveApi, passport, Passport.class);
        return new ResponseEntity<>(
                respPassport,
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Passport> update(@RequestBody Passport passport) {
        HttpEntity<Passport> request = new HttpEntity<>(passport);
        return rest.exchange(updateApi,
                HttpMethod.PATCH, request, Passport.class);
    }

    @DeleteMapping("/delete/{series}/{number}")
    public ResponseEntity<Passport> delete(@PathVariable String series,
                                           @PathVariable String number) {
        return rest.exchange(deleteApi + series + "/" + number,
                HttpMethod.DELETE, null, Passport.class, series, number);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> find() {
        return rest.exchange(findApi,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {});
    }

    @GetMapping("/find/{series}")
    public ResponseEntity<List<Passport>> find(@PathVariable String series) {
        return rest.exchange(findApi + "/" + series,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {}, series);
    }

    @GetMapping("/find/{series}/{number}")
    public ResponseEntity<Passport> find(@PathVariable String series,
                                         @PathVariable String number) {
        return rest.exchange(findApi + "/" + series + "/" + number,
                HttpMethod.GET, null, Passport.class, series, number);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findUnavailable() {
        return rest.exchange(unavailableApi,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {});
    }

    @GetMapping("/replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        return rest.exchange(replaceableApi,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() {});
    }
}
