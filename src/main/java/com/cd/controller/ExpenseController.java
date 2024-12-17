package com.cd.controller;

import com.cd.dto.ExpenseUpdateRequest;
import com.cd.entity.Expense;
import com.cd.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(
                expense.getUser().getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate()
        );
    }

    @GetMapping
    public List<Expense> getExpenses(@RequestParam Long userId) {
        return expenseService.getAllExpenses(userId);
    }
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable UUID id, @RequestBody ExpenseUpdateRequest expenseUpdateRequest) {
        LocalDate parsedDate = LocalDate.parse(expenseUpdateRequest.getDate());
        return expenseService.updateExpense(id, expenseUpdateRequest.getTitle(), expenseUpdateRequest.getAmount(), expenseUpdateRequest.getCategory(), parsedDate);
    }


    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@RequestParam Long userId, @PathVariable String category) {
        return expenseService.getExpensesByCategory(userId, category);
    }

    @GetMapping("/date-range")
    public List<Expense> getExpensesByDateRange(@RequestParam Long userId,
                                                @RequestParam String startDate,
                                                @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return expenseService.getExpensesByDateRange(userId, start, end);
    }
}
