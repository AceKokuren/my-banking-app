package com.virginmoney.bankingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    @Temporal(TemporalType.DATE)
    private LocalDate transactionDate;
    private String transactionVendor;
    private String transactionType;
    @Column(columnDefinition = "decimal", precision = 10, scale = 2)
    private BigDecimal transactionAmount;
    private String transactionCategory;

    public Transaction(LocalDate transactionDate, String vendor, String type, BigDecimal amount, String category) {
        super();
        this.transactionDate = transactionDate;
        this.transactionVendor = vendor;
        this.transactionType = type;
        this.transactionAmount = amount;
        this.transactionCategory = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + transactionDate + '\'' +
                ", vendor='" + transactionVendor + '\'' +
                ", type='" + transactionType + '\'' +
                ", amount='" + transactionAmount + '\'' +
                ", category='" + transactionCategory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionVendor, that.transactionVendor) && Objects.equals(transactionType, that.transactionType) && Objects.equals(transactionAmount, that.transactionAmount) && Objects.equals(transactionCategory, that.transactionCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDate, transactionVendor, transactionType, transactionAmount, transactionCategory);
    }
}
