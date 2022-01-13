package com.expensemanager.project.tests;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.models.ExpenseModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseModelTest {
    ExpenseModel expenseModel = new ExpenseModel();

    @org.junit.jupiter.api.Test
    void addExpense() {
        ExpenseDTO expense = new ExpenseDTO(1, "500", "USD", "for fun");
        try {
            String actual = expenseModel.addExpense(expense);
            String expected = "";
            assertEquals(expected, actual);

            expense.setCategoryId(3);
            actual = expenseModel.addExpense(expense);
            assertEquals("wrong category\n", actual);

            expense.setInfo("");
            expense.setCategoryId(4);
            actual = expenseModel.addExpense(expense);
            assertEquals("info can't be empty\n", actual);

            expense.setInfo("1");
            expense.setCost("-1");
            actual = expenseModel.addExpense(expense);
            assertEquals("cost can't be negative\n", actual);


        } catch (ProjectException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void deleteExpense() {
        int id = 20;

        try {
            boolean actual = expenseModel.deleteExpense(id);
            boolean expected = true;

            assertEquals(expected, actual);

            actual = expenseModel.deleteExpense(id);
            assertEquals(false, actual);

            id = -1;
            actual = expenseModel.deleteExpense(id);
            assertEquals(false, actual);


        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void getExpense() {
        try {
            Expense expense = ExpenseModel.getExpense(1);
            assertEquals(1, expense.getId());
            assertEquals("f u", expense.getInfo());
        } catch (ProjectException e) {
            e.printStackTrace();
        }


    }
}