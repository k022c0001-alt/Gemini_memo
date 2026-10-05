package com.example.bank.dto;

import java.math.BigDecimal;

public record DepositRequest(
    BigDecimal amount
) {}
