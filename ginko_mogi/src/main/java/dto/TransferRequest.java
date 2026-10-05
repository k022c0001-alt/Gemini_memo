package com.example.bank.dto;

import java.math.BigDecimal;

public record TransferRequest(
    String fromAccountNumber,
    String toAccountNumber,
    BigDecimal amount
) {}
