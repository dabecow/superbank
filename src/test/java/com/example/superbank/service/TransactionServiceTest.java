package com.example.superbank.service;

import com.example.superbank.BaseSuperBankTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransactionServiceTest extends BaseSuperBankTest {

    @Test
    void processTransactionWithCommission_belowMinimalCommission_bobHasZero() {
        BigDecimal valueToSend = BigDecimal.valueOf(Math.max(COMMISSION_MIN_VALUE.doubleValue() - 1, 0));

        stubFeignClientCommissionResponse(valueToSend, valueToSend);

        transactionService.processTransactionWithCommission(JAKE, BOB, valueToSend);

        Assertions.assertEquals(BigDecimal.ZERO.intValue(), userAccountService.getUserAccountByName(BOB).getMoneySum().intValue());
    }

    @Test
    void processTransactionWithCommission_aboveMinimalCommission_bobHasValueWithoutCommission() {
        BigDecimal valueToSend = BigDecimal.valueOf(Math.max(COMMISSION_MIN_VALUE.doubleValue() * 2, 0));

        stubFeignClientCommissionResponse(valueToSend, COMMISSION_MIN_VALUE);

        transactionService.processTransactionWithCommission(JAKE, BOB, valueToSend);

        Assertions.assertEquals(valueToSend.subtract(COMMISSION_MIN_VALUE).intValue(), userAccountService.getUserAccountByName(BOB).getMoneySum().intValue());
    }
}
