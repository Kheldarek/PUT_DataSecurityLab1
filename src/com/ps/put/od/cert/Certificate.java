package com.ps.put.od.cert;

/**
 * Created by Kheldar on 2016-11-08.
 */
public class Certificate {

    String name;
    String surname;
    String mail;
    String phone;
    String validFrom;
    String validTo;
    String publisher;
    String version;
    String sha256;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }


    public Certificate(String name, String surname, String mail, String phone, String validFrom, String validTo) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.publisher = "PsendrowskiSuperMegaAwesomeCertificates";
        this.version = "v1337";

    }

    public Certificate(){}





}
