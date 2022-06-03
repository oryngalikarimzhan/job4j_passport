package ru.job4j.passport.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastname;
    private Timestamp birthday;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "expiration_date")
    private Timestamp expirationDate;
    private String citizenship;

    public static Passport build(String name,
                                 String lastname,
                                 Timestamp birthday,
                                 String serialNumber,
                                 Timestamp expirationDate,
                                 String citizenship) {
        Passport passport = new Passport();
        passport.name = name;
        passport.lastname = lastname;
        passport.birthday = birthday;
        passport.serialNumber = serialNumber;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
        return id == passport.id && Objects.equals(serialNumber, passport.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber);
    }
}
