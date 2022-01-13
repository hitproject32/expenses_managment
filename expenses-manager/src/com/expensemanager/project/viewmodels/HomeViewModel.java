package com.expensemanager.project.viewmodels;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.Home.IHomeModel;
import com.expensemanager.project.interfaces.Home.IHomeViewModel;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.models.HomeModel;
import com.expensemanager.project.views.HomeView;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel implements IViewModel, IHomeViewModel {
    private IHomeModel model;
    private IView view;
    private ExecutorService service;

    public HomeViewModel() {
        service = Executors.newFixedThreadPool(3);
    }

    @Override
    public void setModel(IModel model) {
        if (model instanceof IHomeModel) {
            this.model = (IHomeModel) model;
        }
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }


    /**
     * get all expenses from the DB and save then in List
     */
    @Override
    public void setExpensesList(int id) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Expense> expenseList = model.getAllExpenses(id);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            ((HomeView) view).getAllExpenses(expenseList);
                        }
                    });
                } catch (ProjectException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getCategoryNameById(int id) {
        try {
            return ((HomeModel) model).getCategoryNameById(id);
        } catch (ProjectException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void deleteSelected(int id)  {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ((HomeModel)model).deleteSelected(id);
                    setExpensesList(Helper.loggedInAccount.getId());
                } catch (ProjectException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
