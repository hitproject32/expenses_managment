package com.expensemanager.project.interfaces.Category;

import com.expensemanager.project.classes.Category;

public interface ICategoryViewModel {
    public void addCategory(Category category);

    //public void updateCategory(Category category) ;
    public void deleteCategory(String categoryName);

    public void getAllCategories();

}
