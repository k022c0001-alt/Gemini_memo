package com.example.bank.model;

import com.example.bank.exception.InsufficientBalanceException;
import java.math.BigDecimal;

public class Account {
    private final String accountNumber;
    private final String holderName;
    private BigDecimal balance;

    public Account(String accountNumber, String holderName, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public BigDecimal getBalance() { return balance; }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("入金額は1円以上を指定してください。");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("出金額は1円以上を指定してください。");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("残高不足です。現在の残高: " + this.balance + "円");
        }
        this.balance = this.balance.subtract(amount);
    }
}
