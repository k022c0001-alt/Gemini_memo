package com.example.bank.dto;

import com.example.bank.model.Account;
import java.math.BigDecimal;

public class AccountResponse {
    private String accountNumber;
    private String holderName;
    private BigDecimal balance;

    public AccountResponse() {}

    public AccountResponse(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.holderName = account.getHolderName();
        this.balance = account.getBalance();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public BigDecimal getBalance() { return balance; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
