package com.expensemanager.project.helpers;

import com.expensemanager.project.classes.Account;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.models.*;
import com.expensemanager.project.viewmodels.*;
import com.expensemanager.project.views.*;

import javax.swing.*;
import java.util.HashMap;

public class Helper {

    public static Account loggedInAccount;
    public static HashMap<String,Float> currencies = new HashMap<>();

    static {
        currencies.put("ILS",1f);
        currencies.put("USD",3.11f);
        currencies.put("EURO",3.51f);
    }

    /**
     * open new "Screen" - JFRAME.
     * by inserting view, model and viewmodel.
     */
    private static void setScreen(IView view, IModel model, IViewModel viewModel) {
        viewModel.setModel(model);
        viewModel.setView(view);
        view.setViewModel(viewModel);
        view.init();
        view.start();
    }

    public static void showScreen(String name) {

        switch (name) {
            case "Home":
                setScreen(new HomeView(), new HomeModel(), new HomeViewModel());
                break;

            case "Category":
                setScreen(new CategoryView(), new CategoryModel(), new CategoryViewModel());
                break;

            case "AddExpense":
                setScreen(new AddExpenseView(), new AddExpenseModel(), new AddExpenseViewModel());
                break;

            case "Login":
                setScreen(new LoginView(), new LoginModel(), new LoginViewModel());
                break;

            case "Register":
                setScreen(new RegisterView(), new RegisterModel(), new RegisterViewModel());
                break;

            case "Reports":
                setScreen(new ReportsView(), new ReportModel(), new ReportsViewModel());
                break;
        }
    }


    public static void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isNumeric(String message) {
        try {
            Float.parseFloat(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getIconPathByCategoryName(String categoryName) {
        String path = "";
        switch (categoryName) {
            case "Food":
                path = "/images/food_icon.png";
                break;
            case "Household":
                path = "/images/home_icon.png";
                break;
            case "Loans":
                path = "/images/loan_icon.png";
                break;
            case "Automobile":
                path = "/images/automobile_icon.png";
                break;
            case "Travel":
                path = "/images/travel_icon.png";
                break;
            default:
                path = "/images/default_icon.png";
                break;
        }
        return path;
    }



}
