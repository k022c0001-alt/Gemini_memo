package com.example.bank.cli;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BankConsoleUi {
    private final BankService bankService;
    private final Scanner scanner = new Scanner(System.in);

    public BankConsoleUi(BankService bankService) {
        this.bankService = bankService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("     模擬銀行システム (CLI)     ");
            System.out.println("=================================");
            System.out.println("1: 口座開設");
            System.out.println("2: 残高確認");
            System.out.println("3: 預金");
            System.out.println("4: 引き出し");
            System.out.println("5: 送金");
            System.out.println("6: 取引履歴（明細）表示");
            System.out.println("9: 終了");
            System.out.print("メニュー番号を入力してください: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1" -> handleCreateAccount();
                    case "2" -> handleCheckBalance();
                    case "3" -> handleDeposit();
                    case "4" -> handleWithdraw();
                    case "5" -> handleTransfer();
                    case "6" -> handleShowHistory();
                    case "9" -> {
                        System.out.println("システムを終了します。ご利用ありがとうございました。");
                        return;
                    }
                    default -> System.out.println("【警告】 1〜6、または9を入力してください。");
                }
            } catch (Exception e) {
                System.out.println("【エラー】 " + e.getMessage());
            }
        }
    }

    private void handleCreateAccount() {
        System.out.print("口座番号を入力: ");
        String accNum = scanner.nextLine();
        System.out.print("名義人を入力: ");
        String name = scanner.nextLine();
        System.out.print("初期預金額を入力: ");
        BigDecimal balance = new BigDecimal(scanner.nextLine());

        Account account = bankService.createAccount(accNum, name, balance);
        System.out.println("✔ 口座を開設しました: " + account.getHolderName() + " 様 (口座番号: " + account.getAccountNumber() + ")");
    }

    private void handleCheckBalance() {
        System.out.print("口座番号を入力: ");
        String accNum = scanner.nextLine();
        Account account = bankService.getAccount(accNum);
        System.out.println("口座番号: " + account.getAccountNumber() + " | 名義: " + account.getHolderName() + " | 残高: " + account.getBalance() + " 円");
    }

    private void handleDeposit() {
        System.out.print("口座番号を入力: ");
        String accNum = scanner.nextLine();
        System.out.print("入金額を入力: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        bankService.deposit(accNum, amount);
        System.out.println("✔ 入金が完了しました。");
    }

    private void handleWithdraw() {
        System.out.print("口座番号を入力: ");
        String accNum = scanner.nextLine();
        System.out.print("出金額を入力: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        bankService.withdraw(accNum, amount);
        System.out.println("✔ 引き出しが完了しました。");
    }

    private void handleTransfer() {
        System.out.print("出金元口座番号: ");
        String from = scanner.nextLine();
        System.out.print("振込先口座番号: ");
        String to = scanner.nextLine();
        System.out.print("送金額を入力: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        bankService.transfer(from, to, amount);
        System.out.println("✔ 送金が完了しました。");
    }

    private void handleShowHistory() {
        System.out.print("口座番号を入力: ");
        String accNum = scanner.nextLine();
        List<Transaction> history = bankService.getTransactionHistory(accNum);
        
        System.out.println("\n--- 取引履歴 (" + accNum + ") ---");
        if (history.isEmpty()) {
            System.out.println("取引履歴はありません。");
        } else {
            history.forEach(System.out::println);
        }
    }
}
