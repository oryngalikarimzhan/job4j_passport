package ru.job4j.passport.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"series", "number"}),
        @UniqueConstraint(columnNames = "number")
})
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastname;
    private Timestamp birthday;
    private String series;
    private String number;
    @Column(name = "expiration_date")
    private Timestamp expirationDate;
    private String citizenship;

    public static Passport build(String name,
                                 String lastname,
                                 Timestamp birthday,
                                 String series,
                                 String number,
                                 Timestamp expirationDate,
                                 String citizenship) {
        Passport passport = new Passport();
        passport.name = name;
        passport.lastname = lastname;
        passport.birthday = birthday;
        passport.series = series;
        passport.number = number;
        passport.expirationDate = expirationDate;
        passport.citizenship = citizenship;
        return passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return Objects.equals(birthday, passport.birthday) && Objects.equals(series, passport.series) && Objects.equals(number, passport.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, series, number);
    }
}
