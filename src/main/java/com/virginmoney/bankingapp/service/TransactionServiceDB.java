package com.virginmoney.bankingapp.service;

import com.virginmoney.bankingapp.entity.Transaction;
import com.virginmoney.bankingapp.repo.TransactionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceDB implements TransactionService {
    private final TransactionRepo repo;

    public TransactionServiceDB(TransactionRepo repo) {
        super();
        this.repo = repo;
    }

    @Override
    public List<Transaction> getTransactions() {
        return repo.findAll();
    }

    @Override
    public List<Transaction> listTransactionsForCategory(String category) {
        return List.of();
    }
}
