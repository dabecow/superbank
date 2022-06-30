package com.example.superbank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccount sender;

    @ManyToOne
    private UserAccount receiver;

    private BigDecimal sum;

    public Transaction(UserAccount sender, UserAccount receiver, BigDecimal sum) {
        this.sender = sender;
        this.receiver = receiver;
        this.sum = sum;
    }
}
