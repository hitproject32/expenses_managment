package com.expensemanager.project.tests;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.models.CategoryModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryModelTest {
    CategoryModel categoryModel = new CategoryModel();

    @org.junit.jupiter.api.Test
    void getCategory() {
        Category category = null;
        try {
            category = CategoryModel.getCategory(1);
            assertEquals(1, category.getId());
            assertEquals("sports", category.getName());
        } catch (ProjectException e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void addCategory() {
        Category category = new Category("play time");
        try {
            String actual = categoryModel.addCategory(category);
            String expected = "";
            assertEquals(expected, actual);

            category.setName("");
            actual = categoryModel.addCategory(category);
            assertEquals("name of the category can't be empty\n", actual);


        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void deleteCategory() {
        try {
            boolean yap = categoryModel.deleteCategory("Test");
            assertEquals(true, yap);

            yap = categoryModel.deleteCategory("Test 2");
            assertEquals(false, yap);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }
}