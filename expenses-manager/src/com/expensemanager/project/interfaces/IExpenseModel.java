package com.expensemanager.project.interfaces;

import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;

public interface IExpenseModel {
    public String addExpense(ExpenseDTO expense) throws ProjectException;

    public boolean deleteExpense(int id) throws ProjectException;
}
