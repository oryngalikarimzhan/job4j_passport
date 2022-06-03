package ru.job4j.passport.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.passport.domain.Passport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Integer> {
    Optional<Passport> findBySerialNumber(String serialNumber);

    List<Passport> findAllByExpirationDateBefore(Timestamp timestamp);

    List<Passport> findAllByExpirationDateBetween(Timestamp from, Timestamp to);
}
