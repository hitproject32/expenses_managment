package com.expensemanager.project.models;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.interfaces.Home.IHomeModel;
import com.expensemanager.project.interfaces.IModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeModel implements IModel, IHomeModel {

    /**
     * getting all the expenses of the account by account_id.
     */
    @Override
    public List<Expense> getAllExpenses(int accountId) throws ProjectException {
        List<Expense> returnList = new ArrayList<>(5);
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM expenses WHERE account_id = " + accountId;
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
                    returnList.add(expense);

                } while (rs.next());
            }


        } catch (SQLException throwables) {
            throw new ProjectException("HomeModel, getAllExpenses method. error: " + throwables.getMessage());
        }

        return returnList;
    }

    /**
     * returning category name by the category id
     */

    @Override
    public String getCategoryNameById(int id) throws ProjectException {
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

    /**
     * deleting the selected row of the table by the id of the row.
     */
    @Override
    public void deleteSelected(int id) throws ProjectException {
        try (Connection connection = DataBase.getConnection()){
            String query = "DELETE FROM expenses WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
