package ru.job4j.client.domain;

public class EmailMessage {
    private String sender;
    private String receiver;
    private String text;
    private Passport passport;

    public String getSender() {
        return sender;
    }

    public EmailMessage(String sender, String receiver, String text, Passport passport) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.passport = passport;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
