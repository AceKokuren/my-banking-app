package com.virginmoney.bankingapp.service;

import com.virginmoney.bankingapp.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions();

    List<Transaction> getTransactionsByCategory(String category);

    List<Object[]> getTransactionsTotalPerCategory();

    List<Number> getTransactionsByCategoryMonthlyAvg(String category);

    List<Number> getAnnualSpendForCategoryInYear(String category, String year);
}
