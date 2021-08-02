package com.retail.rewards.service;

import com.retail.rewards.CSVHelper;
import com.retail.rewards.entity.ConsumerTransaction;
import com.retail.rewards.modal.ConsumerPoints;
import com.retail.rewards.modal.MonthlyRewards;
import com.retail.rewards.repository.ConsumerTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class RewardsService {

    @Autowired
    private ConsumerTransactionRepository repository;

    @Transactional
    public void save(MultipartFile file) {
        try {
            List<ConsumerTransaction> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<ConsumerPoints> getConsumerPointsForLastThreeMonths() {

        LocalDate localDateThreeMonthsBefore =  LocalDate.now().minusMonths(3);
        List<ConsumerTransaction> transactions = repository.getAllTransactionsForLastThreeMonths(localDateThreeMonthsBefore);
        return convertToConsumerPoints(transactions);
    }

    private List<ConsumerPoints> convertToConsumerPoints(List<ConsumerTransaction> transactions) {

        Map<Long, Map<String, MonthlyRewards>> monthlyRewardsMap = new HashMap<>();
        Map<Long, ConsumerTransaction> consumerMap = new HashMap<>();
        for(ConsumerTransaction consumerTransaction : transactions) {
            Long consumerId = consumerTransaction.getConsumerId();
            consumerMap.put(consumerId, consumerTransaction); //This is to Identify Consumer by ConsumerId
            String month = consumerTransaction.getTransaction_date().getMonth().toString();
            int points = getPoints(consumerTransaction.getTransaction_amount());
            Map<String, MonthlyRewards> consumerMonthlyRewards = monthlyRewardsMap.get(consumerId);
            if(consumerMonthlyRewards == null) consumerMonthlyRewards = new HashMap<>();
                MonthlyRewards monthlyRewards = consumerMonthlyRewards.get(month);
                if(monthlyRewards == null) monthlyRewards = new MonthlyRewards(month);
                monthlyRewards.setPointsForMonth(monthlyRewards.getPointsForMonth() + points);
            consumerMonthlyRewards.put(month, monthlyRewards);
            monthlyRewardsMap.put(consumerId, consumerMonthlyRewards);
        }

        List<ConsumerPoints> consumerPointsList = new ArrayList<>(monthlyRewardsMap.size());
        for(Map.Entry<Long, Map<String, MonthlyRewards>> entry : monthlyRewardsMap.entrySet()) {
            ConsumerPoints consumerPoints = new ConsumerPoints();
            consumerPoints.setCustomerId(entry.getKey());
            consumerPoints.setFirstName(consumerMap.get(entry.getKey()).getFirst_name());
            consumerPoints.setLastName(consumerMap.get(entry.getKey()).getLast_name());
            consumerPoints.setMonthlyRewards(new ArrayList<>(entry.getValue().values()));
            Optional<Integer> totalPoints = entry.getValue().values().stream()
                    .map(monthlyRewards -> monthlyRewards.getPointsForMonth())
                    .reduce((val1, val2) -> val1 + val2);
            consumerPoints.setTotalPoints(totalPoints.orElse(0));
            consumerPointsList.add(consumerPoints);
        }

        return consumerPointsList;
    }

    private int getPoints(Double transaction_amount) {

        int absValue = transaction_amount.intValue();
        if(absValue <= 50) {
            return absValue;
        } if(absValue <=100) {
            return 50;
        }
        else {
            return 50 + (absValue - 100) * 2;
        }
    }
}
