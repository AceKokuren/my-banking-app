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
    public List<Transaction> getTransactionsByCategory(String category) {
        return repo.findTransactionsByTransactionCategoryIgnoreCaseOrderByTransactionDateDesc(category);
    }

    @Override
    public List<Object[]> getTransactionsTotalPerCategory() {
        return repo.getTransactionsByCategoryTotals();
    }

    @Override
    public List<Number> getTransactionsByCategoryMonthlyAvg(String category) {
        return repo.getMonthlySpendByCategory(category);
    }

    @Override
    public List<Number> getAnnualSpendForCategoryInYear(String category, String year) {
        return repo.getAnnualSpendByCategory(category, year);
    }
}
