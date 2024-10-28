package com.virginmoney.bankingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virginmoney.bankingapp.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:transaction-schema.sql", "classpath:transaction-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    List<Transaction> buildTransactions() {
        Transaction transactionOne = new Transaction(1, LocalDate.of(2020, 11, 1),
                "Morrisons", "card", new BigDecimal("10.40"), "Groceries");

        Transaction transactionTwo = new Transaction(2, LocalDate.of(2020, 10, 28),
                "CYBG", "direct debit", new BigDecimal("600.00"), "MyMonthlyDD");

        Transaction transactionThree = new Transaction(3, LocalDate.of(2020, 10, 28),
                "PureGym", "direct debit", new BigDecimal("40.00"), "MyMonthlyDD");

        Transaction transactionFour = new Transaction(4, LocalDate.of(2020, 10, 1),
                "M&S", "card", new BigDecimal("5.99"), "Groceries");

        Transaction transactionFive = new Transaction(5, LocalDate.of(2020, 9, 30),
                "McMillan", "internet", new BigDecimal("10.00"), "");

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(transactionOne);
        transactions.add(transactionTwo);
        transactions.add(transactionThree);
        transactions.add(transactionFour);
        transactions.add(transactionFive);

        return transactions;
    }

    @Test
    void testGetTransactions() throws Exception {
        List<Transaction> transactions = buildTransactions();
        String transactionsAsJson = mapper.writeValueAsString(transactions);

        this.mvc.perform(get("/transactions"))
                .andExpect(content().json(transactionsAsJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactionsByCategory() throws Exception {
        List<Transaction> transactions = buildTransactions();
        List<Transaction> groceries = transactions.stream()
                .filter(el -> el.getTransactionCategory().equalsIgnoreCase("groceries"))
                .toList();

        String groceriesAsJson = mapper.writeValueAsString(groceries);
        this.mvc.perform(get("/transactions/groceries"))
                .andExpect(content().json(groceriesAsJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactionsTotalPerCategory() throws Exception {
        List<Transaction> transactions = buildTransactions();

        String transactionsByCategoryAsJson = "[\n" +
                "    [\n" +
                "        \"\",\n" +
                "        10.00\n" +
                "    ],\n" +
                "    [\n" +
                "        \"Groceries\",\n" +
                "        16.39\n" +
                "    ],\n" +
                "    [\n" +
                "        \"MyMonthlyDD\",\n" +
                "        640.00\n" +
                "    ]\n" +
                "]";

        this.mvc.perform(get("/transactions/category-totals"))
                .andExpect(content().json(transactionsByCategoryAsJson))
                .andExpect(status().isOk());

    }

    @Test
    void testGetTransactionsByCategoryMonthlyAvg() throws Exception {
        List<Transaction> transactions = buildTransactions();

        this.mvc.perform(get("/transactions/monthly-avg/groceries"))
                .andExpect(content().string("8.20"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactionsByCategoryMaxYear() throws Exception {
        this.mvc.perform(get("/transactions/max/groceries-2020"))
                .andExpect(content().string("10.40"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTransactionsByCategoryMinYear() throws Exception {
        this.mvc.perform(get("/transactions/min/groceries-2020"))
                .andExpect(content().string("5.99"))
                .andExpect(status().isOk());
    }

}
