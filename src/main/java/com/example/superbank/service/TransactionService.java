package com.example.superbank.service;

import com.example.superbank.dto.TransactionDto;
import com.example.superbank.model.Transaction;
import com.example.superbank.model.UserAccount;
import com.example.superbank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //вопрос про зависимость между сервисами
public class TransactionService {

    private final UserAccountService userAccountService;
    private final MathFeignService mathService;
    private final TransactionRepository transactionRepository;

    public void processTransactionWithCommission(String nameFrom, String nameTo, BigDecimal moneySum) {
        processTransactionWithCommission(userAccountService.getUserAccountByName(nameFrom), userAccountService.getUserAccountByName(nameTo), moneySum);
    }

    @Transactional
    private void processTransactionWithCommission(UserAccount from, UserAccount to, BigDecimal moneySum) {
        BigDecimal commission = mathService.getCommission(moneySum);
        processTransaction(from, userAccountService.getSuperBankAccount(), commission);

        BigDecimal sumWithoutCommission = moneySum.subtract(commission);
        if (sumWithoutCommission.compareTo(BigDecimal.ZERO) < 0)
            sumWithoutCommission = BigDecimal.ZERO;

        processTransaction(from, to, sumWithoutCommission);
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream().map(t -> new TransactionDto(t.getSender().getName(), t.getReceiver().getName(), t.getSum())).collect(Collectors.toList());
    }

    @Transactional
    private void processTransaction(UserAccount from, UserAccount to, BigDecimal moneySum){
        userAccountService.removeSumForUser(from, moneySum);
        userAccountService.addSumForUser(to, moneySum);
        Transaction transaction = new Transaction(from, to, moneySum);
        transactionRepository.save(transaction);
    }

}
