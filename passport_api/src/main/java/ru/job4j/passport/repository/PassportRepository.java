package ru.job4j.passport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.passport.domain.Passport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
@Repository
public interface PassportRepository extends CrudRepository<Passport, Integer> {
    Optional<Passport> findBySeriesAndNumber(String series, String number);
    List<Passport> findBySeries(String series);
    List<Passport> findAllByExpirationDateBefore(Timestamp timestamp);
    List<Passport> findAllByExpirationDateBetween(Timestamp from, Timestamp to);
}
