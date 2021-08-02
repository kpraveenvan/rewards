package com.retail.rewards.repository;

import com.retail.rewards.entity.ConsumerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsumerTransactionRepository extends JpaRepository<ConsumerTransaction, Long> {

    @Query("SELECT ct FROM ConsumerTransaction ct where ct.transaction_date >= :localDate ")
    List<ConsumerTransaction> getAllTransactionsForLastThreeMonths(@Param("localDate") LocalDate localDate);
}
