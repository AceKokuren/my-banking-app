package com.virginmoney.bankingapp.service;

import com.virginmoney.bankingapp.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions();

    List<Transaction> listTransactionsForCategory(String category);
}
