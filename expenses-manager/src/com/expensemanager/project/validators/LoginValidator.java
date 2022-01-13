package com.expensemanager.project.validators;

import com.expensemanager.project.dtos.AccountLoginDTO;
import com.expensemanager.project.interfaces.IValidator;

public class LoginValidator implements IValidator {


    /**
     * validate Login by checking:
     * 1) there are no empty fields.
     */
    @Override
    public String validate(Object object) {
        if (object != null && object instanceof AccountLoginDTO) {
            AccountLoginDTO client = (AccountLoginDTO) object;
            StringBuilder stringBuilder = new StringBuilder();
            if (client.getUsername().isEmpty() || client.getPassword().isEmpty()) {
                stringBuilder.append("One of the fields is empty\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }
}

