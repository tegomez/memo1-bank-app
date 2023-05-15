package com.aninfo.model;
import javax.persistence.*;
import javax.persistence.Entity;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TransactionType type;

    private String name;

    private Double amount;

    private Long accountCbu;

    private Double balance;

    public Transaction() {
    }

    public Transaction(TransactionType type, String name, Double amount, Long accountCbu) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.accountCbu = accountCbu;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getAccountCbu() {
        return accountCbu;
    }

    public void setAccountCbu(Long accountCbu) {
        this.accountCbu = accountCbu;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}