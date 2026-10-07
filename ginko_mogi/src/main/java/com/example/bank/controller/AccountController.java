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

    // 引き出し (POST /api/accounts/{accountNumber}/withdraw) ★追加
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable String accountNumber,
            @RequestBody WithdrawRequest request) {
        bankService.withdraw(accountNumber, request.amount());
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
2. 設定ファイル (application.properties)
配置場所: src/main/resources/application.properties
Properties

# サーバーの起動ポート番号
server.port=8080

# アプリケーション名
spring.application.name=bank-app

# ログ設定（開発用に独自処理のログを出力）
logging.level.com.example.bank=DEBUG
3. 自動テストコード (BankServiceTest.java)
銀行システムでは特に重要な「送金」や「残高不足チェック」が正しく動くか自動でテストするコードです。
配置場所: src/test/java/com/example/bank/service/BankServiceTest.java
Java

package com.example.bank.service;

import com.example.bank.exception.InsufficientBalanceException;
import com.example.bank.model.Account;
import com.example.bank.repository.InMemoryAccountRepository;
import com.example.bank.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService(
                new InMemoryAccountRepository(),
                new InMemoryTransactionRepository()
        );
        bankService.createAccount("1001", "テスト太郎", new BigDecimal("10000"));
        bankService.createAccount("1002", "テスト花子", new BigDecimal("5000"));
    }

    @Test
    @DisplayName("正常系: 送金処理により両口座の残高が正しく更新されること")
    void testTransferSuccess() {
        bankService.transfer("1001", "1002", new BigDecimal("3000"));

        Account acc1 = bankService.getAccount("1001");
        Account acc2 = bankService.getAccount("1002");

        assertEquals(new BigDecimal("7000"), acc1.getBalance());
        assertEquals(new BigDecimal("8000"), acc2.getBalance());
    }

    @Test
    @DisplayName("異常系: 残高不足の場合に例外が発生し送金が行われないこと")
    void testTransferInsufficientBalance() {
        assertThrows(InsufficientBalanceException.class, () -> {
            bankService.transfer("1001", "1002", new BigDecimal("20000"));
        });
    }
}
