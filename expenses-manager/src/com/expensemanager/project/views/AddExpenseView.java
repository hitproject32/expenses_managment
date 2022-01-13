package com.expensemanager.project.views;

import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.NewExpense.INewExpenseViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AddExpenseView extends JFrame implements IView {
    private INewExpenseViewModel viewModel;
    private JLabel labelTitle, labelCategories, labelCost, labelCurrency, labelInfo;
    private JButton btnAddExpense, btnCancel;
    private JTextField textFieldCost;
    private JTextArea textAreaInfo;
    private GridBagConstraints constraints;
    private JComboBox<String> comboBoxCategories, comboBoxCurrencies;


    public AddExpenseView() {

    }


    @Override
    public void init() {
        labelTitle = new JLabel("Add New Expense");
        labelCategories = new JLabel("Categories: ");
        labelCurrency = new JLabel("Currency: ");
        labelInfo = new JLabel("Info: ");
        labelCost = new JLabel("Cost: ");
        btnAddExpense = new JButton("Add Expense");
        btnCancel = new JButton("Return To Homepage");
        comboBoxCategories = new JComboBox<>();
        comboBoxCurrencies = new JComboBox<>();
        textFieldCost = new JTextField(15);
        textAreaInfo = new JTextArea(5, 15);
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
        this.add(labelCategories, constraints);


        viewModel.getAllCategories();
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(comboBoxCategories, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(labelCost, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldCost, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(labelCurrency, constraints);

        setCurrencies();
        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(comboBoxCurrencies, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(labelInfo, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        this.add(textAreaInfo, constraints);


        constraints.gridx = 0;
        constraints.gridy = 5;
        this.add(btnAddExpense, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        this.add(btnCancel, constraints);
        setLocationRelativeTo(null);
        this.pack();


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHomeScreen();
            }
        });

        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = comboBoxCategories.getSelectedItem().toString();
                String currency = comboBoxCurrencies.getSelectedItem().toString();
                String cost = textFieldCost.getText();
                String info = textAreaInfo.getText();
                int categoryId = viewModel.getCategoryIdByName(categoryName);

                viewModel.addNewExpense(new ExpenseDTO(categoryId, cost, currency, info));

            }
        });

    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof INewExpenseViewModel) {
            this.viewModel = (INewExpenseViewModel) viewModel;
        }

    }

    @Override
    public void showMessage(String msg) {
        Helper.showMessage("Categories", msg);
    }

    public void showHomeScreen() {
        dispose();
        Helper.showScreen("Home");
    }


    public void loadCategoriesNamesInit(List<String> names) {
        for (String s : names) {
            comboBoxCategories.addItem(s);
        }
    }

    public void setCurrencies() {
        for(Map.Entry set:Helper.currencies.entrySet()){
            comboBoxCurrencies.addItem(set.getKey().toString());
    }
    }
}
