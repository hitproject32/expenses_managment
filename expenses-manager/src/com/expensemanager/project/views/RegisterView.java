package com.expensemanager.project.views;

import com.expensemanager.project.dtos.AccountRegisterDTO;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Register.IRegisterViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame implements IView {
    private IRegisterViewModel viewModel;
    private JLabel labelTitle, labelUsername, labelPassword, labelRePassword;
    private JButton btnLogin, btnRegister;
    private JTextField textFieldUsername;
    private JPasswordField textFieldPassword, textFieldRePassword;
    private GridBagConstraints constraints;

    public RegisterView() {
    }

    public void init() {
        labelTitle = new JLabel("Register");
        labelUsername = new JLabel("username: ");
        labelPassword = new JLabel("password: ");
        labelRePassword = new JLabel("confirm your password: ");
        btnLogin = new JButton("log in");
        btnRegister = new JButton("create account");
        textFieldUsername = new JTextField(15);
        textFieldPassword = new JPasswordField(15);
        textFieldRePassword = new JPasswordField(15);
        constraints = new GridBagConstraints();
    }

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
        this.add(labelRePassword, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(textFieldRePassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(btnRegister, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        this.add(btnLogin, constraints);

        setLocationRelativeTo(null);
        this.pack();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username, password, rePassword;
                username = textFieldUsername.getText();
                password = textFieldPassword.getText();
                rePassword = textFieldRePassword.getText();
                AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(username, password, rePassword);
                viewModel.registerNewClient(accountRegisterDTO);
            }
        });
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof IRegisterViewModel)
            this.viewModel = (IRegisterViewModel) viewModel;
    }

    @Override
    public void showMessage(String message) {
        Helper.showMessage("Register", message);
    }

    public void showLoginScreen() {
        dispose();
        Helper.showScreen("Login");
    }

}


