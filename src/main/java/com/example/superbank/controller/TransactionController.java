package com.example.superbank.controller;

import com.example.superbank.dto.TransactionDto;
import com.example.superbank.model.Transaction;
import com.example.superbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/process")
    public void processTransaction(@RequestParam String nameFrom,
                                   @RequestParam String nameTo,
                                   @RequestParam BigDecimal moneySum) {
        transactionService.processTransactionWithCommission(nameFrom, nameTo, moneySum);
    }

    @GetMapping("/all")
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

}
