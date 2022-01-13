package com.expensemanager.project.models;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.Report.IReportModel;
import com.expensemanager.project.validators.ReportsValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportModel implements IReportModel, IModel {

    /**
     * get all expenses of the current logged user by two dates
     */
    @Override
    public List<Expense> getReport(String fromDateStr, String toDateStr) throws ProjectException {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        List<Expense> filterredExpenses = new ArrayList<>();
        ReportsValidator validator = new ReportsValidator();
        String errorFrom = validator.validate(fromDateStr);
        String errorTo = validator.validate(toDateStr);
        if (!errorFrom.isEmpty()) {
            throw new ProjectException("From date field errors:\n" + errorFrom);
        } else if (!errorTo.isEmpty()) {
            throw new ProjectException("To date field errors:\n" + errorTo);
        } else {

            try (Connection connection = DataBase.getConnection()) {
                Date fromDate = simpleDateFormat.parse(fromDateStr);
                Date toDate = simpleDateFormat.parse(toDateStr);

                long fromDateL = fromDate.getTime();
                long toDateL = toDate.getTime();
                String query = "SELECT * FROM expenses WHERE account_id = " + Helper.loggedInAccount.getId() + " AND date_created >= " + fromDateL + " AND date_created <= " + toDateL;
                ResultSet rs = DataBase.selectAll(connection, query);

                if (rs != null) {
                    do {
                        Expense expense = new Expense();
                        expense.setId(rs.getInt("id"));
                        expense.setInfo(rs.getString("info"));
                        expense.setCost(rs.getFloat("cost"));
                        expense.setCurrency(rs.getString("currency"));
                        expense.setCategoryId(rs.getInt("category_id"));
                        expense.setDateCreated(new Date(rs.getLong("date_created")));
                        expense.setCategoryName(getCategoryNameById(expense.getCategoryId()));
                        filterredExpenses.add(expense);
                    }
                    while (rs.next());
                } else {
                    throw new ProjectException("there are no expenses matches your dates");
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return filterredExpenses;
    }

    /**
     * get the category name by category id
     */
    private String getCategoryNameById(int id) throws ProjectException {
        if (id > 0) {
            try (Connection connection = DataBase.getConnection()) {

                String query = "SELECT name FROM categories WHERE id = " + id;
                ResultSet rs = DataBase.selectAll(connection, query);

                if (rs != null) {
                    return rs.getString("name");
                }

            } catch (SQLException throwables) {
                throw new ProjectException(throwables.getMessage());
            }

        }

        return "";
    }
}

