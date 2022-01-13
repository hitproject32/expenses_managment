package com.expensemanager.project;

import com.expensemanager.project.models.LoginModel;
import com.expensemanager.project.viewmodels.LoginViewModel;
import com.expensemanager.project.views.LoginView;

import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginModel loginModel = new LoginModel();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginView.init();
                loginView.start();
            }
        });
        loginViewModel.setView(loginView);
        loginViewModel.setModel(loginModel);
        loginView.setViewModel(loginViewModel);

    }
}
