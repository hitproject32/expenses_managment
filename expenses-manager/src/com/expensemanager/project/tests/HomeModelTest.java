package com.expensemanager.project.tests;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.models.HomeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HomeModelTest {
HomeModel homeModel;
int id;

@BeforeEach
private void init(){
    homeModel = new HomeModel();
    id = 2;
}

    @Test
    void getAllExpenses() {
        List<Expense> expenses = new LinkedList<>();
        try {
            expenses = homeModel.getAllExpenses(id);
            assertEquals(2,expenses.size());

        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getCategoryNameById() {
    String categoryName;
    try{
      categoryName = homeModel.getCategoryNameById(1);
        assertEquals(categoryName,"Food");
    } catch (ProjectException e) {
        e.printStackTrace();
    }
    }

}