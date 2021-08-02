package com.retail.rewards.modal;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ConsumerPoints {

    private Long customerId;
    private String firstName;
    private String lastName;
    List<MonthlyRewards> monthlyRewards;
    private double totalPoints;
}
