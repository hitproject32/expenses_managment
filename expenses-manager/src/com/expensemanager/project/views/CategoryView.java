package com.expensemanager.project.views;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.Category.ICategoryViewModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CategoryView extends JFrame implements IView {

    private ICategoryViewModel viewModel;
    private JLabel labelTitle, labelCategories, labelNewCategory;
    private JButton btnAddCategory, btnDeleteCategory, btnCancel;
    private JTextField textFieldCategory;
    private GridBagConstraints constraints;
    private JComboBox<String> comboBoxCategories;
    private List<String> categoriesList;

    public CategoryView() {

    }


    @Override
    public void init() {
        labelTitle = new JLabel("Manage Categories");
        labelCategories = new JLabel("Categories: ");
        labelNewCategory = new JLabel("New Category Name: ");
        btnAddCategory = new JButton("Add Category");
        btnDeleteCategory = new JButton("Delete Category");
        btnCancel = new JButton("Return To Homepage");
        comboBoxCategories = new JComboBox<>();
        textFieldCategory = new JTextField(15);
        constraints = new GridBagConstraints();
        categoriesList = new ArrayList<>();
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
        this.add(labelNewCategory, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldCategory, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(btnAddCategory, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(btnDeleteCategory, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(btnCancel, constraints);
        setLocationRelativeTo(null);
        this.pack();


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHomeScreen();
            }
        });

        btnAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldCategory.getText();
                viewModel.addCategory(new Category(name));
            }
        });

        btnDeleteCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = comboBoxCategories.getSelectedItem().toString();
                viewModel.deleteCategory(categoryName);
            }
        });
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof ICategoryViewModel) {
            this.viewModel = (ICategoryViewModel) viewModel;
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

    public void addCategory(String category) {
        categoriesList.add(category);
        updateCategoriesList();
        textFieldCategory.setText("");
    }

    public void loadCategoriesNamesInit(List<String> names) {
        for (String s : names) {
            comboBoxCategories.addItem(s);
            categoriesList.add(s);
        }
    }

    public void updateCategoriesNamesList(String categoryName) {

    }

    public void updateCategoriesList() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(categoriesList.toArray());
        comboBoxCategories.setModel(comboBoxModel);
    }

    public void deleteCategory(String category) {
        categoriesList.remove(category);
        updateCategoriesList();
    }


}
