package br.gabriel.gbank.models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agency;
    private String accountNumber;
    private BigDecimal currentBalance;

    public Account() {}

    public Account(Long id, String agency, String accountNumber, BigDecimal currentBalance) {
        this.id = id;
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;

    }

    public Account(String accountNumber, String agency, BigDecimal currentBalance) {
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.currentBalance = currentBalance;
    }

    public Long getId() {
        return id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

}
