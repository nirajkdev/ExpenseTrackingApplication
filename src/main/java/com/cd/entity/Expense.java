package com.cd.entity;



import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID ExpenseId;

    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Expense() {

    }

    public Expense(UUID id, String title, double amount, String category, LocalDate date, UserEntity user) {
        this.ExpenseId = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;
    }



    public UUID getExpenseId() {
        return ExpenseId;
    }

    public void setExpenseId(UUID expenseId) {
        ExpenseId = expenseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        if (this.ExpenseId == null) {
            this.ExpenseId = UUID.randomUUID();
        }
    }

}
