package com.retail.rewards.modal;

import lombok.Data;

@Data
public class MonthlyRewards {

    private String month;
    private int         pointsForMonth;

    public MonthlyRewards() {
    }

    public MonthlyRewards(String month) {
        this.month = month;
    }
}
