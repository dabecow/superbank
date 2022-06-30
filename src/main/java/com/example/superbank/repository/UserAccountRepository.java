package com.example.superbank.repository;

import com.example.superbank.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    boolean existsByName(String name);

    UserAccount getByName(String bankName);
}
