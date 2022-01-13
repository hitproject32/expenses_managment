package com.expensemanager.project.validators;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.dtos.ExpenseDTO;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.models.CategoryModel;

public class ExpenseValidator implements IValidator {

    /**
     * validate Expense by checking:
     * 1) its not null or there are empty fields.
     * 2) cost is above 0.
     * 3) valid category id by checking it in the DB.
     */
    @Override
    public String validate(Object object) {
        if (object != null && object instanceof ExpenseDTO) {
            ExpenseDTO expense = (ExpenseDTO) object;
            StringBuilder errorsBuilder = new StringBuilder();

            try {
                if (!Helper.isNumeric(expense.getCost())) {
                    errorsBuilder.append("cost cannot be string,please enter only numbers");
                } else if (Float.parseFloat(expense.getCost()) < 0) {
                    errorsBuilder.append("cost can't be negative\n");
                }

                Category category = CategoryModel.getCategory(expense.getCategoryId());
                if (category == null) {
                    errorsBuilder.append("wrong category\n");
                }

                if (expense.getInfo().isEmpty()) {
                    errorsBuilder.append("you need to add some info\n");
                }
                return errorsBuilder.toString();

            } catch (ProjectException e) {
                e.printStackTrace();
            }


        }
        return null;
    }
}
