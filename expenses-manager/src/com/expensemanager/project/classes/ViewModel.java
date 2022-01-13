package com.expensemanager.project.classes;

import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ViewModel implements IViewModel {
    protected IView view;
    protected IModel model;
    protected ExecutorService executors = Executors.newFixedThreadPool(3);

}
