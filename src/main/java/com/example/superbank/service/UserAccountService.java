package com.example.superbank.service;

import com.example.superbank.model.UserAccount;
import com.example.superbank.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Value("${accounts.bank}")
    private String bankName;

    @PostConstruct
    void initBank() {
        if (!userAccountRepository.existsByName(bankName))
            userAccountRepository.save(new UserAccount(bankName));
    }

    @Transactional
    public void addSumForUser(String userName, BigDecimal moneySum) {
        UserAccount userAccount = getUserAccountByName(userName);
        userAccount.setMoneySum(userAccount.getMoneySum().add(moneySum));
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public void addSumForUser(UserAccount userAccount, BigDecimal moneySum) {
        userAccount.setMoneySum(userAccount.getMoneySum().add(moneySum));
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public void removeSumForUser(UserAccount userAccount, BigDecimal moneySum) {
        userAccount.setMoneySum(userAccount.getMoneySum().subtract(moneySum));
        userAccountRepository.save(userAccount);
    }

    public UserAccount getSuperBankAccount(){
        return userAccountRepository.getByName(bankName);
    }

    public UserAccount createUserAccount(String name) {
        UserAccount userAccount = new UserAccount(name);
        return userAccountRepository.save(userAccount);
    }

    public List<UserAccount> getUserAccounts() {
        return userAccountRepository.findAll();
    }

    public UserAccount getUserAccountByName(String name) {
        return userAccountRepository.getByName(name);
    }
}
