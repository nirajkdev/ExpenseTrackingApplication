package com.cd.service;

import com.cd.entity.Expense;
import com.cd.entity.UserEntity;
import com.cd.repository.ExpenseRepository;
import com.cd.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;

    private UserEntity user;
    private Expense expense;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        expense = new Expense(UUID.randomUUID(), "Test Expense", 100.0, "Food", LocalDate.now(), user);
    }

    @Test
    public void testGetAllExpenses() {
        when(expenseRepository.findByUserId(1L)).thenReturn(Arrays.asList(expense));
        var expenses = expenseService.getAllExpenses(1L);
        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        verify(expenseRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testGetExpenseById() {
        UUID expenseId = expense.getExpenseId();
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        var fetchedExpense = expenseService.getExpenseById(expenseId);
        assertNotNull(fetchedExpense);
        assertEquals(expenseId, fetchedExpense.getExpenseId());
        verify(expenseRepository, times(1)).findById(expenseId);
    }

    @Test
    public void testGetExpenseById_NotFound() {
        UUID expenseId = UUID.randomUUID();
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> expenseService.getExpenseById(expenseId));
    }

    @Test
    public void testAddExpense() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        var addedExpense = expenseService.addExpense(1L, "Test Expense", 100.0, "Food", LocalDate.now());
        assertNotNull(addedExpense);
        assertEquals("Test Expense", addedExpense.getTitle());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void testAddExpense_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> expenseService.addExpense(1L, "Test Expense", 100.0, "Food", LocalDate.now()));
    }

    @Test
    public void testUpdateExpense() {
        UUID expenseId = expense.getExpenseId();
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        var updatedExpense = expenseService.updateExpense(expenseId, "Updated Expense", 150.0, "Transport", LocalDate.now());
        assertNotNull(updatedExpense);
        assertEquals("Updated Expense", updatedExpense.getTitle());
        assertEquals(150.0, updatedExpense.getAmount());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void testUpdateExpense_NotFound() {
        UUID expenseId = UUID.randomUUID();
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> expenseService.updateExpense(expenseId, "Updated Expense", 150.0, "Transport", LocalDate.now()));
    }

    @Test
    public void testDeleteExpense() {
        UUID expenseId = expense.getExpenseId();
        doNothing().when(expenseRepository).deleteById(expenseId);
        expenseService.deleteExpense(expenseId);
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    public void testGetExpensesByCategory() {
        when(expenseRepository.findByUserIdAndCategory(1L, "Food")).thenReturn(Arrays.asList(expense));
        var expensesByCategory = expenseService.getExpensesByCategory(1L, "Food");
        assertNotNull(expensesByCategory);
        assertEquals(1, expensesByCategory.size());
        verify(expenseRepository, times(1)).findByUserIdAndCategory(1L, "Food");
    }

    @Test
    public void testGetExpensesByDateRange() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(expenseRepository.findByUserIdAndDateBetween(1L, startDate, endDate)).thenReturn(Arrays.asList(expense));
        var expensesByDateRange = expenseService.getExpensesByDateRange(1L, startDate, endDate);
        assertNotNull(expensesByDateRange);
        assertEquals(1, expensesByDateRange.size());
        verify(expenseRepository, times(1)).findByUserIdAndDateBetween(1L, startDate, endDate);
    }
}

