package com.example.bank.controller;

import com.example.bank.dto.*;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final BankService bankService;

    // コンストラクタ注入 (Springが自動的にBankServiceをインジェクションします)
    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    // 口座開設 (POST /api/accounts)
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = bankService.createAccount(
                request.accountNumber(),
                request.holderName(),
                request.initialBalance()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponse(account));
    }

    // 残高・口座情報の取得 (GET /api/accounts/{accountNumber})
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        Account account = bankService.getAccount(accountNumber);
        return ResponseEntity.ok(new AccountResponse(account));
    }

    // 預金 (POST /api/accounts/{accountNumber}/deposit)
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable String accountNumber,
            @RequestBody DepositRequest request) {
        bankService.deposit(accountNumber, request.amount());
        Account updatedAccount = bankService.getAccount(accountNumber);
        return ResponseEntity.ok(new AccountResponse(updatedAccount));
    }

    // 送金 (POST /api/accounts/transfer)
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        bankService.transfer(
                request.fromAccountNumber(),
                request.toAccountNumber(),
                request.amount()
        );
        return ResponseEntity.ok("送金処理が成功しました。");
    }

    // 取引履歴の取得 (GET /api/accounts/{accountNumber}/transactions)
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable String accountNumber) {
        List<Transaction> transactions = bankService.getTransactionHistory(accountNumber);
        List<TransactionResponse> response = transactions.stream()
                .map(TransactionResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }
}
