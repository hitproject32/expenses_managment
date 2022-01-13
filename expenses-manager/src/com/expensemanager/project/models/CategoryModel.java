package com.expensemanager.project.models;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.Category.ICategoryModel;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.validators.CategoryValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel implements IModel, ICategoryModel {
    private IValidator validator = new CategoryValidator();


    /**
     * getting category from DB by the category_id.
     */
    public static Category getCategory(int id) throws ProjectException {
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM categories WHERE id = '" + id + "'";
            ResultSet rs = DataBase.selectAll(connection, query);

            if (rs != null) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setId(rs.getInt("id"));

                return category;
            }


        } catch (SQLException throwables) {
            throw new ProjectException("DBmodel: getCategory error. " + throwables.getMessage());
        }

        return null;
    }

    /**
     * get the category object by category name
     */
    public static Category getCategory(String categoryName) throws ProjectException {
        try (Connection connection = DataBase.getConnection()) {

            String query = "SELECT * FROM categories WHERE name = '" + categoryName + "'";
            ResultSet rs = DataBase.selectAll(connection, query);

            if (rs != null) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setId(rs.getInt("id"));
                category.setAccountId(rs.getInt("account_id"));

                return category;
            }


        } catch (SQLException throwables) {
            throw new ProjectException("DBmodel: getCategory error. " + throwables.getMessage());
        }

        return null;
    }

    /**
     * add new category into the DB.
     */
    @Override
    public String addCategory(Category category) throws ProjectException {
        String errors = validator.validate(category);
        if (errors.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "INSERT INTO categories (name,account_id) VALUES(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, category.getName());
                preparedStatement.setInt(2, Helper.loggedInAccount.getId());
                preparedStatement.execute();

            } catch (SQLException throwables) {
                throw new ProjectException("DBmodel: addCategory error. " + throwables.getMessage());
            }
        }
        return errors;
    }


    /**
     * delete category from the DB
     */
    @Override
    public boolean deleteCategory(String categoryName) throws ProjectException {
        Category category = CategoryModel.getCategory(categoryName);
        if (category != null && category.getAccountId() != 0) {
            try (Connection connection = DataBase.getConnection()) {
                String query = "DELETE FROM categories WHERE name=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.execute();

                query = "DELETE FROM expenses WHERE category_id = " + category.getId() + " AND account_id = " + Helper.loggedInAccount.getId();
                Statement statement = connection.createStatement();
                statement.execute(query);

                return true;
            } catch (SQLException throwables) {
                throw new ProjectException("DBmodel: deleteCategory error. " + throwables.getMessage());
            }
        } else if (category.getAccountId() == 0) {
            throw new ProjectException("You cant delete root category");
        }
        return false;
    }

    /**
     * get all categories names
     */
    @Override
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


}
