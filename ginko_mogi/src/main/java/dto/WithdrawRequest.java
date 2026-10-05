package com.example.bank.dto;

import java.math.BigDecimal;

public record WithdrawRequest(
    BigDecimal amount
) {}
