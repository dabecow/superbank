package com.example.superbank.controller;

import com.example.superbank.model.UserAccount;
import com.example.superbank.service.UserAccountService;
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
@RequestMapping("/users")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/create")
    public void createUser(@RequestParam String name) {
        userAccountService.createUserAccount(name);
    }

    @GetMapping("/all")
    public List<UserAccount> getAllUsers() {
        return userAccountService.getUserAccounts();
    }

    @PostMapping("/add-money")
    public void addMoneyForUser(@RequestParam String userName,
                                @RequestParam BigDecimal moneySum) {
        userAccountService.addSumForUser(userName, moneySum);
    }

}
