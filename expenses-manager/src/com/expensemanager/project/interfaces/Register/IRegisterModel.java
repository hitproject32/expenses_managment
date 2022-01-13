package com.expensemanager.project.interfaces.Register;

import com.expensemanager.project.dtos.AccountRegisterDTO;
import com.expensemanager.project.exceptions.ProjectException;

public interface IRegisterModel {
    public String createAccount(AccountRegisterDTO client) throws ProjectException;
}
