package com.expensemanager.project.models;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.interfaces.IExpenseModel;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.validators.ExpenseValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseModel implements IModel, IExpenseModel {

    private IValidator validator = new ExpenseValidator();

    /**
     * get expense from DB by user id.
     */
    public static Expense getExpense(int id) throws ProjectException {
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM expenses WHERE id = '" + id + "'";
            ResultSet rs = DataBase.selectAll(connection, query);

            if (rs != null) {
                Expense expense = new Expense();
                expense.setCategoryId(rs.getInt("category_id"));
                expense.setCurrency(rs.getString("currency"));
                expense.setCost(rs.getFloat("cost"));
                expense.setInfo(rs.getString("info"));
                expense.setId(rs.getInt("id"));

                return expense;
            }


        } catch (SQLException throwables) {
            throw new ProjectException("ExpensesModel, getExpense. error: " + throwables.getMessage());
        }

        return null;
    }

    /**
     * add an expense into DB.
     * using HomeValidator for data validation.
     * see validators/HomeValidator.
     */
    @Override
    public String addExpense(ExpenseDTO expense) throws ProjectException {
        String errors = validator.validate(expense);
        if (errors != null && errors.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "INSERT INTO expenses (category_name,cost,currency_id,info) VALUES(?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, expense.getCategoryId());
                preparedStatement.setFloat(2, Float.parseFloat(expense.getCost()));
                preparedStatement.setString(3, expense.getCurrency());
                preparedStatement.setString(4, expense.getInfo());
                preparedStatement.execute();

            } catch (SQLException throwables) {
                throw new ProjectException("ExpensesModel, addExpense. error: " + throwables.getMessage());
            }
        }
        return errors;
    }

    /**
     * delete an expense from DB by expense id.
     */
    @Override
    public boolean deleteExpense(int id) throws ProjectException {
        Expense expense = getExpense(id);
        if (expense != null) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "DELETE FROM expenses WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                return true;
            } catch (SQLException throwables) {
                throw new ProjectException("ExpensesModel, deleteExpense. error: " + throwables.getMessage());
            }
        }
        return false;
    }


}
