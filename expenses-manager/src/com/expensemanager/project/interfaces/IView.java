package com.expensemanager.project.interfaces;

public interface IView {
    public void init();

    public void start();

    public void setViewModel(IViewModel viewModel);

    public void showMessage(String msg);

}
