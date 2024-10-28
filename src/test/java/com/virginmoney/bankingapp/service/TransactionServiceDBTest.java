package com.virginmoney.bankingapp.service;

import com.virginmoney.bankingapp.entity.Transaction;
import com.virginmoney.bankingapp.repo.TransactionRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@Sql(scripts = {"classpath:transaction-schema.sql", "classpath:transaction-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TransactionServiceDBTest {

    @Autowired
    private TransactionServiceDB service;

    @MockBean
    private TransactionRepo repo;

    List<Transaction> buildTransactionList() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transactionOne = new Transaction(1, LocalDate.of(2020, 11, 1),
                "'Morrisons'", "card", new BigDecimal("10.40"), "'Groceries'");

        Transaction transactionTwo = new Transaction(2, LocalDate.of(2020, 10, 28),
                "CYBG", "direct debit", new BigDecimal("600.00"), "MyMonthlyDD");

        Transaction transactionThree = new Transaction(3, LocalDate.of(2020, 10, 28),
                "PureGym", "direct debit", new BigDecimal("40.00"), "MyMonthlyDD");
        Transaction transactionFour = new Transaction(4, LocalDate.of(2020, 10, 1),
                "M&S", "card", new BigDecimal("5.99"), "Groceries");

        Transaction transactionFive = new Transaction(5, LocalDate.of(2020, 9, 30),
                "McMillan", "internet", new BigDecimal("10.00"), "");

        transactions.add(transactionOne);
        transactions.add(transactionTwo);
        transactions.add(transactionThree);
        transactions.add(transactionFour);
        transactions.add(transactionFive);

        return transactions;
    }

    @Test
    void testGetTransactions() {
        List<Transaction> transactions = buildTransactionList();

        Mockito.when(this.repo.findAll()).thenReturn(transactions);

        Assertions.assertThat(this.service.getTransactions()).isEqualTo(transactions);

        Mockito.verify(this.repo, Mockito.times(1)).findAll();
    }

    @Test
    void testGetTransactionsByCategory() {
        List<Transaction> transactions = buildTransactionList();

        List<Transaction> filteredTransactions = transactions.stream()
                .filter(el -> el.getTransactionCategory()
                        .equalsIgnoreCase("groceries"))
                .toList();

        Mockito.when(this.repo.findTransactionsByTransactionCategoryIgnoreCaseOrderByTransactionDateDesc(anyString()))
                .thenReturn(filteredTransactions);

        Assertions.assertThat(this.service.getTransactionsByCategory("groceries")).isEqualTo(filteredTransactions);

        Mockito.verify(this.repo, Mockito.times(1))
                .findTransactionsByTransactionCategoryIgnoreCaseOrderByTransactionDateDesc(anyString());

    }

    @Test
    void testGetTransactionsTotalPerCategory() {
        Object[] objOne = {"", new BigDecimal("10.00")};
        Object[] objTwo = {"Groceries", new BigDecimal("16.39")};
        Object[] objThree = {"MyMonthlyDD", new BigDecimal("640.00")};

        List<Object[]> categoryTotals = Arrays.asList(objOne, objTwo, objThree);

        Mockito.when(this.repo.getTransactionsByCategoryTotals())
                .thenReturn(categoryTotals);

        Assertions.assertThat(this.service.getTransactionsTotalPerCategory()).isEqualTo(categoryTotals);

        Mockito.verify(this.repo, Mockito.times(1)).getTransactionsByCategoryTotals();
    }

    @Test
    void testGetTransactionsByCategoryMonthlyAvg() {
        List<Number> monthlySpend = List.of(new BigDecimal("10.40"), new BigDecimal("5.99"));

        Mockito.when(this.repo.getMonthlySpendByCategory(anyString()))
                .thenReturn(monthlySpend);

        Assertions.assertThat(this.service.getTransactionsByCategoryMonthlyAvg("groceries")).isEqualTo(monthlySpend);

        Mockito.verify(this.repo, Mockito.times(1)).getMonthlySpendByCategory("groceries");
    }

    @Test
    void testGetAnnualSpendForCategoryInYear() {
        List<Number> annualSpend = List.of(new BigDecimal("10.40"), new BigDecimal("5.99"));

        Mockito.when(this.repo.getAnnualSpendByCategory(anyString(), anyString()))
                .thenReturn(annualSpend);

        Assertions.assertThat(this.service.getAnnualSpendForCategoryInYear("groceries", "2020"))
                .isEqualTo(annualSpend);

        Mockito.verify(this.repo, Mockito.times(1))
                .getAnnualSpendByCategory(anyString(), anyString());
    }

}
