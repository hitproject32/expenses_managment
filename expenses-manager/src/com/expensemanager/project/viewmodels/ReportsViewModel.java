package com.expensemanager.project.viewmodels;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.classes.ViewModel;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Report.IReportModel;
import com.expensemanager.project.interfaces.Report.IReportViewModel;
import com.expensemanager.project.views.ReportsView;

import java.util.List;

public class ReportsViewModel extends ViewModel implements IViewModel, IReportViewModel {
    @Override
    public void setModel(IModel model) {
        if (model instanceof IReportModel) {
            this.model = model;
        }
    }

    @Override
    public void setView(IView view) {
        if (view instanceof ReportsView) {
            this.view = view;
        }
    }

    @Override
    public void getReport(String fromDateStr, String toDateStr) {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Expense> expensesList = ((IReportModel) model).getReport(fromDateStr, toDateStr);
                    ((ReportsView) view).displayExpense(expensesList);
                } catch (ProjectException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }
}


