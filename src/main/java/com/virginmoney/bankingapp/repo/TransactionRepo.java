package com.virginmoney.bankingapp.repo;

import com.virginmoney.bankingapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findTransactionsByTransactionCategoryIgnoreCaseOrderByTransactionDateDesc(String category);

    @Query(value = "SELECT t.transactionCategory, SUM(t.transactionAmount) FROM Transaction t GROUP BY t.transactionCategory")
    List<Object[]> getTransactionsByCategoryTotals();

    @Query(value = "SELECT SUM(t.transactionAmount) FROM Transaction t WHERE LOWER(t.transactionCategory) = LOWER(:category) GROUP BY MONTH(t.transactionDate), YEAR(t.transactionDate)")
    List<Number> getMonthlySpendByCategory(String category);

    @Query(value = "SELECT SUM(t.transactionAmount) FROM Transaction t WHERE YEAR(t.transactionDate) = :year AND LOWER(t.transactionCategory) = LOWER(:category) GROUP BY MONTH(t.transactionDate)")
    List<Number> getAnnualSpendByCategory(String category, String year);
}
