package com.example.bank.service;

import com.example.bank.model.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    // 簡易データベース（メモリ保持）
    private final Map<String, Account> accountMap = new HashMap<>();

    // 口座開設
    public Account createAccount(String accountNumber, String holderName, BigDecimal initialBalance) {
        if (accountMap.containsKey(accountNumber)) {
            throw new IllegalArgumentException("既に存在する口座番号です。");
        }
        Account account = new Account(accountNumber, holderName, initialBalance);
        accountMap.put(accountNumber, account);
        return account;
    }

    // 口座検索
    public Account getAccount(String accountNumber) {
        Account account = accountMap.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("口座が見つかりません: " + accountNumber);
        }
        return account;
    }

    // 預金
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
    }

    // 引き出し
    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
    }

    // 送金
    public void transfer(String fromAccNum, String toAccNum, BigDecimal amount) {
        Account from = getAccount(fromAccNum);
        Account to = getAccount(toAccNum);
        
        from.withdraw(amount);
        to.deposit(amount);
    }
}
