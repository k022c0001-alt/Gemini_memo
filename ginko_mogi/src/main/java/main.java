package com.example.bank;

import com.example.bank.cli.BankConsoleUi;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.InMemoryAccountRepository;
import com.example.bank.repository.InMemoryTransactionRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.BankService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // リポジトリとサービスの初期化 (依存性の注入: DI)
        AccountRepository accountRepository = new InMemoryAccountRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        BankService bankService = new BankService(accountRepository, transactionRepository);

        // 動作確認用の初期データ投入
        initializeSampleData(bankService);

        // CLI起動
        BankConsoleUi ui = new BankConsoleUi(bankService);
        ui.start();
    }

    private static void initializeSampleData(BankService bankService) {
        // テスト用口座を作成
        bankService.createAccount("1001", "田中 太郎", new BigDecimal("500000"));
        bankService.createAccount("1002", "鈴木 花子", new BigDecimal("100000"));
    }
}
