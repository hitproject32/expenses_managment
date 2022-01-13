package com.expensemanager.project.viewmodels;

import com.expensemanager.project.classes.Category;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.interfaces.Category.ICategoryModel;
import com.expensemanager.project.interfaces.Category.ICategoryViewModel;
import com.expensemanager.project.interfaces.IModel;
import com.expensemanager.project.interfaces.IValidator;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.models.CategoryModel;
import com.expensemanager.project.validators.CategoryValidator;
import com.expensemanager.project.views.CategoryView;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryViewModel implements IViewModel, ICategoryViewModel {
    private IView view;
    private ICategoryModel model;
    private ExecutorService executor;
    private IValidator validator;

    public CategoryViewModel() {
        executor = Executors.newFixedThreadPool(3);
        validator = new CategoryValidator();
    }


    @Override
    public void setModel(IModel model) {
        if (model instanceof CategoryModel) {
            this.model = (ICategoryModel) model;
        }

    }

    @Override
    public void setView(IView view) {
        if (view instanceof CategoryView) {
            this.view = view;
        }
    }

    @Override
    public void addCategory(Category category) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String errors = model.addCategory(category);
                    if (errors.isEmpty()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ((CategoryView) view).addCategory(category.getName());
                                ((CategoryView) view).showMessage("Category added successfully");
                            }
                        });
                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                view.showMessage(errors);
                            }
                        });
                    }
                } catch (ProjectException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /*@Override
    public void updateCategory(Category category) {

    }*/

    @Override
    public void deleteCategory(String categoryName) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isDeleted = model.deleteCategory(categoryName);
                    if (isDeleted) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ((CategoryView) view).deleteCategory(categoryName);
                                view.showMessage("Category deleted successfully");
                            }
                        });
                    }
                } catch (ProjectException e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            view.showMessage(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getAllCategories() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> names = model.getAllCategories();
                    if (names != null) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ((CategoryView) view).loadCategoriesNamesInit(names);
                            }
                        });
                    }
                } catch (ProjectException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
