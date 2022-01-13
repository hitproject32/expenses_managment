package com.expensemanager.project.interfaces.Report;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;

import java.util.List;

public interface IReportModel {
    List<Expense> getReport(String fromDateStr, String toDateStr) throws ProjectException;
}
