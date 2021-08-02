package com.retail.rewards.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "consumer_transactions")
public class ConsumerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long consumerId;
    private String first_name;
    private String last_name;
    private LocalDate transaction_date;
    private Double transaction_amount;

    public ConsumerTransaction() {
    }

    public ConsumerTransaction(long consumerId, String first_name, String last_name, LocalDate transaction_date, Double transaction_amount) {
        this.consumerId = consumerId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.transaction_date = transaction_date;
        this.transaction_amount = transaction_amount;
    }
}
