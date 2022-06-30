package com.example.superbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDto {
    private String senderName;
    private String receiverName;
    private BigDecimal sum;
}
