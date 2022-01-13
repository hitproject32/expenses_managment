package com.expensemanager.project.interfaces.Home;

import com.expensemanager.project.exceptions.ProjectException;

public interface IHomeViewModel {
    public void setExpensesList(int id);

    public String getCategoryNameById(int id);

    void deleteSelected(int id) ;
}
