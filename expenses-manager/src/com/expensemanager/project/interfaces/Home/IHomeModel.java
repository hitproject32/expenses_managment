package com.expensemanager.project.interfaces.Home;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;

import java.util.List;

public interface IHomeModel {
    public List<Expense> getAllExpenses(int id) throws ProjectException;

    public String getCategoryNameById(int id) throws ProjectException;

    void deleteSelected(int id) throws  ProjectException;
}
