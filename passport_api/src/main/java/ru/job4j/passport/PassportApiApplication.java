package ru.job4j.passport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PassportApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportApiApplication.class, args);
    }

}
