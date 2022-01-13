package com.expensemanager.project.tests;

import com.expensemanager.project.classes.Account;
import com.expensemanager.project.classes.Category;
import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.models.AddExpenseModel;
import com.expensemanager.project.models.ExpenseModel;
import com.expensemanager.project.validators.ExpenseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddExpenseModelTest {
    AddExpenseModel addExpenseModel;

    @BeforeEach
    private void init(){
        addExpenseModel = new AddExpenseModel();
        Helper.loggedInAccount = new Account(2,"Test");
    }

    @Test
    void addNewExpense() {
        try {
            ExpenseDTO expenseDTO = new ExpenseDTO(1,"-100","ILS","Testing");
            String errors = addExpenseModel.addNewExpense(expenseDTO);
            assertEquals("cost can't be negative\n",errors);
            expenseDTO.setCost("200");
            errors = addExpenseModel.addNewExpense(expenseDTO);
            assertEquals("",errors);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllCategories() {
        List<String> categories = new LinkedList<>();
        try {
            categories = addExpenseModel.getAllCategories();
            assertEquals(5,categories.size());
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getCategoryIdByName() {
        try {
            int id = addExpenseModel.getCategoryIdByName("Food");
            assertEquals(1,id);
            id = addExpenseModel.getCategoryIdByName("NoSuchCategory");
            assertEquals(0,id);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }
}