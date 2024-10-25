package com.virginmoney.bankingapp.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionTest {
    @Test
    public void testTransactionToString() {
        LocalDate transactionDate = LocalDate.parse("2020-11-01");

        final Transaction TEST_TRANSACTION = new Transaction(transactionDate, "Morrisons", "Card", new BigDecimal("10.40"), "Groceries");
        final String TEST_TRANSACTION_STRING = "Transaction" +
                "{date='2020-11-01', " +
                "vendor='Morrisons', " +
                "type='Card', " +
                "amount='10.40', " +
                "category='Groceries'}";

        Assertions.assertEquals(TEST_TRANSACTION_STRING, TEST_TRANSACTION.toString());
    }
}