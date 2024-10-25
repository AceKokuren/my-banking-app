package com.virginmoney.bankingapp.controller;

import com.virginmoney.bankingapp.entity.Transaction;
import com.virginmoney.bankingapp.repo.TransactionRepo;
import com.virginmoney.bankingapp.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService service;
    private final TransactionRepo repo;

    public TransactionController(TransactionService transactionService, TransactionRepo repo) {
        super();
        this.service = transactionService;
        this.repo = repo;
    }

    @GetMapping("")
    public List<Transaction> getTransactions() {
        return this.service.getTransactions();
    }
}
