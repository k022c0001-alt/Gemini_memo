package com.example.bank.dto;

import java.math.BigDecimal;

public record CreateAccountRequest(
    String accountNumber,
    String holderName,
    BigDecimal initialBalance
) {}
