package com.expensemanager.project.models;

import com.expensemanager.project.dtos.AccountRegisterDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Security;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.Register.IRegisterModel;
import com.expensemanager.project.validators.RegisterValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterModel implements IModel, IRegisterModel {
    RegisterValidator registerValidator = new RegisterValidator();

    /**
     * create new account and insert the data in the DB.
     * hashing the password before inserting the data.
     * using RegisterValidator for data validation.
     * see validators/RegisterValidator.
     */
    @Override
    public String createAccount(AccountRegisterDTO client) throws ProjectException {
        String errors = registerValidator.validate(client);
        StringBuilder errorsBuilder = new StringBuilder(errors);

        if (errorsBuilder.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + client.getUsername() + "'");
                if (rs != null) {
                    errorsBuilder.append("This username all ready exits\n");
                }
                if (errorsBuilder.isEmpty()) {
                    // the mysql insert statement
                    String query = " insert into accounts (username, password_hash)"
                            + " values (?, ?)";

                    String hashStr = Security.sha512Encryption(client.getPassword());

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, client.getUsername());
                    preparedStmt.setString(2, hashStr);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    connection.close();
                }
            } catch (SQLException e) {
                throw new ProjectException("RegisterModel, createAccount. error: " + e.getMessage());
            }
        }


        return errorsBuilder.toString();
    }
}
