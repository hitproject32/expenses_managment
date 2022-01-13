package com.expensemanager.project.viewmodels;

import com.expensemanager.project.classes.Account;
import com.expensemanager.project.dtos.AccountLoginDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.DataBase;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Login.ILoginModel;
import com.expensemanager.project.interfaces.Login.ILoginViewModel;
import com.expensemanager.project.views.LoginView;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel implements ILoginViewModel, IViewModel {
    private ILoginModel loginModel;
    private IView loginView;
    private ExecutorService service;

    public LoginViewModel() {
        service = Executors.newFixedThreadPool(3);
    }

    /**
     * login into the app.
     * checking with the validator if there are no erros and then checking in the DB if the details are correct.
     * using LoginValidator for data validation.
     * see validators/LoginValidator.
     */
    @Override
    public void loginUser(AccountLoginDTO client) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                String errors = null;
                try {
                    errors = loginModel.loginUser(client);
                } catch (ProjectException e) {
                    e.printStackTrace();
                }
                if (errors != null && !errors.isEmpty()) {
                    String finalErrors = errors;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            loginView.showMessage(finalErrors);
                        }
                    });

                } else {
                    int id = DataBase.getAccountId(client.getUsername());
                    Account acc = new Account(id, client.getUsername());
                    Helper.loggedInAccount = acc;
                    ((LoginView) loginView).showHomeScreen();
                }
            }
        });
    }

    @Override
    public void setModel(IModel model) {
        if (model instanceof ILoginModel) {
            this.loginModel = (ILoginModel) model;
        }
    }

    @Override
    public void setView(IView view) {
        this.loginView = view;


    }

}
