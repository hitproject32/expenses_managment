package com.expensemanager.project.views;

import com.expensemanager.project.dtos.AccountLoginDTO;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Login.ILoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements IView {
    private ILoginViewModel loginViewModel;
    private JLabel labelTitle, labelUsername, labelPassword;
    private JPasswordField textFieldPassword;
    private JButton btnLogin, btnRegister;
    private JTextField textFieldUsername;
    private GridBagConstraints constraints;

    public LoginView() {

    }

    @Override
    public void init() {
        labelTitle = new JLabel("Log in");
        labelUsername = new JLabel("username: ");
        labelPassword = new JLabel("password: ");
        btnLogin = new JButton("log in");
        btnRegister = new JButton("create account");
        textFieldUsername = new JTextField(15);
        textFieldPassword = new JPasswordField(15);
        constraints = new GridBagConstraints();
    }

    @Override
    public void start() {
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        this.add(labelTitle, constraints);
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(labelUsername, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(textFieldUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(labelPassword, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(btnLogin, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(btnRegister, constraints);
        setLocationRelativeTo(null);
        this.pack();

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Helper.showScreen("Register");
            }
        });


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username, password;
                username = textFieldUsername.getText();
                password = textFieldPassword.getText();
                AccountLoginDTO accountLoginDTO = new AccountLoginDTO(username, password);
                loginViewModel.loginUser(accountLoginDTO);

            }
        });
    }


    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof ILoginViewModel) {
            this.loginViewModel = (ILoginViewModel) viewModel;
        }
    }

    @Override
    public void showMessage(String message) {
        Helper.showMessage("Login", message);
    }


    public void showHomeScreen() {
        dispose();
        Helper.showScreen("Home");
    }

    public void openMainScreen() {
        this.setVisible(false);
    }
}



