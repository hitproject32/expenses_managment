package com.expensemanager.project.models;

import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.interfaces.NewExpense.INewExpenseModel;
import com.expensemanager.project.validators.ExpenseValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseModel implements IModel, INewExpenseModel {
    private IValidator validator;

    public AddExpenseModel() {
        validator = new ExpenseValidator();
    }

    /**
     * add new expense to the db return errors if their any.
     */
    @Override
    public String addNewExpense(ExpenseDTO expenseDTO) throws ProjectException {
        String errors = validator.validate(expenseDTO);
        if (errors.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "INSERT INTO expenses (account_id,category_id,cost,currency,info,date_created) VALUES(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Helper.loggedInAccount.getId());
                preparedStatement.setInt(2, expenseDTO.getCategoryId());
                preparedStatement.setFloat(3, Float.parseFloat(expenseDTO.getCost()));
                preparedStatement.setString(4, expenseDTO.getCurrency());
                preparedStatement.setString(5, expenseDTO.getInfo());
                preparedStatement.setLong(6, System.currentTimeMillis());
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException throwables) {
                throw new ProjectException(throwables.getMessage());
            }
        }
        return errors;
    }

    /**
     * get all categories names of the current user
     */
    public List<String> getAllCategories() throws ProjectException {
        //String errors = validator.validate() we need to add
        List<String> list = new ArrayList<>();
        try (Connection connection = DataBase.getConnection()) {
            String query = "SELECT name FROM categories WHERE account_id=" + Helper.loggedInAccount.getId() + " OR account_id=0";
            ResultSet rs = DataBase.selectAll(connection, query);
            if (rs != null) {
                do {
                    list.add(rs.getString("name"));
                } while (rs.next());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * return category id by category name
     */
    @Override
    public int getCategoryIdByName(String categoryName) throws ProjectException {
        if (categoryName != null && !categoryName.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "SELECT id FROM categories WHERE name = '" + categoryName + "'";
                ResultSet rs = DataBase.selectAll(connection, query);
                if (rs != null) {
                    return rs.getInt("id");
                }
            } catch (SQLException throwables) {
                throw new ProjectException(throwables.getMessage());
            }
        }

        return 0;
    }
}
