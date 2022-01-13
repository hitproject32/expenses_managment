package com.expensemanager.project.interfaces.Category;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.exceptions.ProjectException;

import java.util.List;

public interface ICategoryModel {
    public String addCategory(Category category) throws ProjectException;

    //public String updateCategory(Category category) throws ProjectException;
    public boolean deleteCategory(String categoryName) throws ProjectException;

    public List<String> getAllCategories() throws ProjectException;

}
