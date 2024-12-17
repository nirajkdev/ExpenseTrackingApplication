package com.cd.service;

import com.cd.entity.Expense;
import com.cd.entity.UserEntity;
import com.cd.repository.ExpenseRepository;
import com.cd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> getAllExpenses(Long userId) {
        return expenseRepository.findByUserId(userId);
    }
    public Expense getExpenseById(UUID id) {
        return expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public Expense addExpense(Long userId, String title, double amount, String category, LocalDate date) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UUID randomExpenseId = UUID.randomUUID();
        Expense expense = new Expense(null, title, amount, category, date, user);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(UUID id, String title, double amount, String category, LocalDate date) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(date);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(Long userId, String category) {
        return expenseRepository.findByUserIdAndCategory(userId, category);
    }

    public List<Expense> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
