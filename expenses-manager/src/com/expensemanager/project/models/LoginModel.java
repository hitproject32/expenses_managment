package com.expensemanager.project.models;

import com.expensemanager.project.dtos.AccountLoginDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Security;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.Login.ILoginModel;
import com.expensemanager.project.validators.LoginValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel implements IModel, ILoginModel {
    LoginValidator loginValidator = new LoginValidator();

    /**
     * method to log in into the app.
     * checking if the data is valid in the DB.
     * using RegisterValidator for data validation.
     * see validators/LoginValidator.
     */
    @Override
    public String loginUser(AccountLoginDTO client) throws ProjectException {
        String errors = loginValidator.validate(client);
        StringBuilder errorsBuilder = new StringBuilder(errors);
        if (errorsBuilder.isEmpty()) {
            try (Connection connection = DataBase.getConnection()) {
                ResultSet rs = DataBase.selectAll(connection, "SELECT * FROM accounts WHERE username = '" + client.getUsername() + "'");

                if (rs != null) {
                    String storedHash = rs.getString("password_hash");
                    String hash = Security.sha512Encryption(client.getPassword());
                    if (!hash.equals(storedHash)) {
                        errorsBuilder.append("Passwords doesn't match");
                    }
                } else {
                    errorsBuilder.append("username is wrong\n");
                }

            } catch (SQLException e) {
                throw new ProjectException("LoginModel, loginUser method. error: " + e.getMessage());
            }

        }

        return errorsBuilder.toString();
    }
}
