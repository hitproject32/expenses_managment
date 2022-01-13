package com.expensemanager.project.viewmodels;

import com.expensemanager.project.dtos.AccountRegisterDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Register.IRegisterModel;
import com.expensemanager.project.interfaces.Register.IRegisterViewModel;
import com.expensemanager.project.views.RegisterView;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterViewModel implements IViewModel, IRegisterViewModel {
    private IView view;
    private IRegisterModel model;
    private ExecutorService service;


    /**
     * Registration to the app.
     * checking with the validator if there are no erros and then checking in the DB if the username is not exists
     * using RegisterModel
     */
    public void registerNewClient(AccountRegisterDTO client) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String errors = model.createAccount(client);
                    if (errors != null && !errors.isEmpty()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                view.showMessage(errors);

                            }
                        });

                    } else {
                        view.showMessage("Registered Successful");
                        ((RegisterView) view).showLoginScreen();

                    }

                } catch (ProjectException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RegisterViewModel() {
        service = Executors.newFixedThreadPool(3);
    }


    @Override
    public void setModel(IModel model) {
        if (model instanceof IRegisterModel) {
            this.model = (IRegisterModel) model;
        }
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }
}
