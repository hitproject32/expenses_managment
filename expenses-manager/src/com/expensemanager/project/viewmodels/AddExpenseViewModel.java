package com.expensemanager.project.viewmodels;

import com.expensemanager.project.classes.ViewModel;
import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.NewExpense.INewExpenseModel;
import com.expensemanager.project.interfaces.NewExpense.INewExpenseViewModel;
import com.expensemanager.project.models.AddExpenseModel;
import com.expensemanager.project.views.AddExpenseView;

import javax.swing.*;
import java.util.List;

public class AddExpenseViewModel extends ViewModel implements INewExpenseViewModel {


    public void getAllCategories() {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> categoriesNames = ((INewExpenseModel) model).getAllCategories();
                    if (!categoriesNames.isEmpty()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ((AddExpenseView) view).loadCategoriesNamesInit(categoriesNames);
                            }
                        });
                    }
                } catch (ProjectException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getCategoryIdByName(String categoryName) {
        int catId = 0;
        try {

            catId = ((AddExpenseModel) model).getCategoryIdByName(categoryName);


        } catch (ProjectException e) {
            e.printStackTrace();
        }

        return catId;
    }


    @Override
    public void addNewExpense(ExpenseDTO expenseDTO) {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                String errors;
                try {
                   errors = ((AddExpenseModel) model).addNewExpense(expenseDTO);
                   if(errors.isEmpty()){
                       SwingUtilities.invokeLater(new Runnable() {
                           @Override
                           public void run() {
                               view.showMessage("Expense added successfully");
                               ((AddExpenseView) view).showHomeScreen();
                           }
                       });
                   }else{
                       SwingUtilities.invokeLater(new Runnable() {
                           @Override
                           public void run() {
                               view.showMessage(errors);
                           }
                       });
                   }

                } catch (ProjectException e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(e.getMessage());
                        }
                    });

                }
            }
        });
    }


    @Override
    public void setModel(IModel model) {
        if (model instanceof INewExpenseModel) {
            this.model = model;
        }

    }

    @Override
    public void setView(IView view) {
        if (view instanceof AddExpenseView) {
            this.view = view;
        }
    }
}
