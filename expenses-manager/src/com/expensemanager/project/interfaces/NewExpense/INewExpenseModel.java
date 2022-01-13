package com.expensemanager.project.interfaces.NewExpense;

import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;

import java.util.List;

public interface INewExpenseModel {
    public String addNewExpense(ExpenseDTO expenseDTO) throws ProjectException;

    public List<String> getAllCategories() throws ProjectException;

    public int getCategoryIdByName(String categoryName) throws ProjectException;
}
