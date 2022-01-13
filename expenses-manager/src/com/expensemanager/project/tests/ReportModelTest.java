package com.expensemanager.project.tests;

import com.expensemanager.project.classes.Account;
import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.models.ReportModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportModelTest {
    ReportModel reportModel;

    @BeforeEach
    private void init(){
        reportModel = new ReportModel();
        Helper.loggedInAccount = new Account(7,"Test2");
    }

    @Test
    void getReport() {
        try {
            String dateFrom = "1-1-2022";
            String dateTo = "5-1-2022";
            List<Expense> expenses = new LinkedList<>();
            String finalDateFrom = dateFrom;
            String finalDateTo = dateTo;
            assertThrows(ProjectException.class,() -> reportModel.getReport(finalDateFrom, finalDateTo));
            dateFrom = "1--1-2022";
            String finalDateFrom1 = dateFrom;
            String finalDateTo1 = dateTo;
            assertThrows(ProjectException.class,() -> reportModel.getReport(finalDateFrom1, finalDateTo1));
            dateFrom = "1-1-2022";
            dateTo = "11-1-2022";
            expenses = reportModel.getReport(dateFrom,dateTo);
            assertEquals(2,expenses.size());
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }


}