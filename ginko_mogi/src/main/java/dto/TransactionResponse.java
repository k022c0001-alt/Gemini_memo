package com.example.bank.dto;

import com.example.bank.model.Transaction;
import java.math.BigDecimal;

public class TransactionResponse {
    private String id;
    private String accountNumber;
    private String type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String timestamp;
    private String description;

    public TransactionResponse() {}

    public TransactionResponse(Transaction t) {
        this.id = t.getId();
        this.accountNumber = t.getAccountNumber();
        this.type = t.getType().getLabel();
        this.amount = t.getAmount();
        this.balanceAfter = t.getBalanceAfter();
        this.timestamp = t.getTimestamp().toString();
        this.description = t.getDescription();
    }

    public String getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    public void setId(String id) { this.id = id; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setType(String type) { this.type = type; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setBalanceAfter(BigDecimal balanceAfter) { this.balanceAfter = balanceAfter; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setDescription(String description) { this.description = description; }
}
