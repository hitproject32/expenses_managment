package com.expensemanager.project.interfaces.Login;

import com.expensemanager.project.dtos.AccountLoginDTO;
import com.expensemanager.project.exceptions.ProjectException;

public interface ILoginModel {
    public String loginUser(AccountLoginDTO client) throws ProjectException;
}
