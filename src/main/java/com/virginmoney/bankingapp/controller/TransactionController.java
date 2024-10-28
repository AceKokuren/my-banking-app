package com.virginmoney.bankingapp.controller;

import com.virginmoney.bankingapp.Utils;
import com.virginmoney.bankingapp.entity.Transaction;
import com.virginmoney.bankingapp.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@RestController
@RequestMapping("transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService transactionService) {
        super();
        this.service = transactionService;
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getTransactions() {
        Utils.debugMsg("Method: getTransactions");

        List<Transaction> transactions = this.service.getTransactions();

        Utils.debugMsgWithObj("Transactions: {}", transactions);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable String category) {
        Utils.debugMsg("Method: getTransactionsByCategory");

        List<Transaction> transactions = service.getTransactionsByCategory(category);

        Utils.debugMsgWithObj("Transactions: {}", transactions);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/category-totals")
    public ResponseEntity<Object[]> getTransactionsTotalPerCategory() {
        Utils.debugMsg("Method: getTransactionsTotalPerCategory");

        List<Object[]> transactions = service.getTransactionsTotalPerCategory();

        Utils.debugMsgWithObj("Transactions: {}", transactions);

        return new ResponseEntity<>(transactions.toArray(), HttpStatus.OK);
    }

    @GetMapping("/monthly-avg/{category}")
    public ResponseEntity<BigDecimal> getTransactionsByCategoryMonthlyAvg(@PathVariable String category) {

        Utils.debugMsg("Method: getTransactionsByCategoryMonthlyAvg");

        BigDecimal totalMonthlyAmount = this.service.getTransactionsByCategoryMonthlyAvg(category)
                .stream()
                .map(el -> new BigDecimal(el.toString()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Utils.debugMsg(totalMonthlyAmount.toString());

        Utils.debugMsgWithObj("totalMonthlyAmount: {}", totalMonthlyAmount);

        BigDecimal averageMonthlySpend = null;

        if (!totalMonthlyAmount.equals(BigDecimal.ZERO)) {
            averageMonthlySpend = totalMonthlyAmount
                    .divide(new BigDecimal(this.service.getTransactionsByCategoryMonthlyAvg(category).size()),
                            2, RoundingMode.HALF_UP);
        }

        Utils.debugMsgWithObj("averageMonthlySpend: {}", averageMonthlySpend);

        return new ResponseEntity<>(averageMonthlySpend, HttpStatus.OK);
    }

    @GetMapping("max/{category}-{year}")
    public ResponseEntity<BigDecimal> getTransactionsByCategoryMaxYear(@PathVariable String category,
                                                                       @PathVariable String year) {


        Utils.debugMsgWithObj("Method: getTransactionsByCategoryMaxYear, Given Year: {}, Given Category: {}",
                year, category);

        List<BigDecimal> totalSpend = this.service.getAnnualSpendForCategoryInYear(category, year)
                .stream()
                .map(el -> new BigDecimal(el.toString()))
                .toList();

        Utils.debugMsgWithObj("Total Spend: {}", totalSpend);

        BigDecimal maxSpend = totalSpend.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        Utils.debugMsgWithObj("Max Spend: {}", maxSpend);

        return new ResponseEntity<>(maxSpend, HttpStatus.OK);
    }

    @GetMapping("min/{category}-{year}")
    public ResponseEntity<BigDecimal> getTransactionsByCategoryMinYear(@PathVariable String category,
                                                                       @PathVariable String year) {

        Utils.debugMsgWithObj("Method: getTransactionsByCategoryMinYear, Given Year: {}, Given Category: {}",
                year, category);

        List<BigDecimal> totalSpend = this.service.getAnnualSpendForCategoryInYear(category, year)
                .stream()
                .map(el -> new BigDecimal(el.toString()))
                .toList();

        Utils.debugMsgWithObj("Total Spend: {}", totalSpend);

        BigDecimal minSpend = totalSpend.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        Utils.debugMsgWithObj("Min Spend: {}", minSpend);

        return new ResponseEntity<>(minSpend, HttpStatus.OK);
    }
}
