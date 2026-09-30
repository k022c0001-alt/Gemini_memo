package com.example.bank.model;

import java.math.BigDecimal;

public class Account {
    private final String accountNumber; // 口座番号
    private final String holderName;    // 名義人
    private BigDecimal balance;         // 残高（お金の計算にはBigDecimalを使います）

    public Account(String accountNumber, String holderName, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public BigDecimal getBalance() { return balance; }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("残高不足です。");
        }
        this.balance = this.balance.subtract(amount);
    }
}
