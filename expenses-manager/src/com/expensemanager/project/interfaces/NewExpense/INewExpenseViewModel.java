package com.expensemanager.project.interfaces.NewExpense;

import com.expensemanager.project.dtos.ExpenseDTO;

public interface INewExpenseViewModel {
    public void addNewExpense(ExpenseDTO expenseDTO);

    public void getAllCategories();

    public int getCategoryIdByName(String categoryName);
}
