package com.expensemanager.project.views;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.exceptions.ProjectException;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.interfaces.Home.IHomeViewModel;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.viewmodels.HomeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class HomeView extends JFrame implements IView {
    private JPanel panelNorth, panelCenter;
    private JTable tableExpense;
    List<Expense> expenseList = new ArrayList<>();
    private JLabel labelTitle;
    private JButton btnAddExpense, btnManageCategory, btnGetReports, btnLogout;
    private GridBagConstraints bagConstraints;
    private IHomeViewModel homeViewModel;
    private JScrollPane scrollPane;

    public HomeView() {

    }

    public void init() {
        tableExpense = new JTable();
        scrollPane = new JScrollPane();
        panelNorth = new JPanel(new GridBagLayout());
        panelCenter = new JPanel(new BorderLayout());
        labelTitle = new JLabel("Expense manager");
        btnAddExpense = new JButton("Add new expense");
        btnManageCategory = new JButton("Manage Category's");
        btnGetReports = new JButton("Get Reports");
        btnLogout = new JButton("Logout");
        bagConstraints = new GridBagConstraints();
        initTable();
    }

    private void initTable() {
        homeViewModel.setExpensesList(Helper.loggedInAccount.getId());
    }

    public void start() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        //SET CUSTOM RENDERER TO TEAMS COLUMN

        bagConstraints.gridwidth = 5;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        panelNorth.add(labelTitle, bagConstraints);

        bagConstraints.gridwidth = 2;
        bagConstraints.gridy = 1;
        panelNorth.add(btnAddExpense, bagConstraints);

        bagConstraints.gridwidth = 1;
        bagConstraints.gridx = 3;
        panelNorth.add(btnManageCategory, bagConstraints);

        bagConstraints.gridx = 4;
        panelNorth.add(btnGetReports, bagConstraints);

        bagConstraints.gridx = 5;
        panelNorth.add(btnLogout, bagConstraints);

        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));


        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        //homeViewModel.setExpensesList(Helper.loggedInAccount.getId());

        this.setSize(900, 600);

        btnManageCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCategoryScreen();
            }
        });

        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExpenseScreen();
            }
        });

        btnGetReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportsScreen();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });



    }



    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof HomeViewModel)
            this.homeViewModel = (HomeViewModel) viewModel;
    }

    @Override
    public void showMessage(String msg) {
        Helper.showMessage("Homepage", msg);
    }

    public void getAllExpenses(List<Expense> expenseList) {
        this.expenseList = expenseList;
        DisplayExpense(expenseList);



    }

    public void showCategoryScreen() {
        dispose();
        Helper.showScreen("Category");
    }

    public void showExpenseScreen() {
        dispose();
        Helper.showScreen("AddExpense");
    }

    public void showReportsScreen() {
        dispose();
        Helper.showScreen("Reports");
    }

    private void DisplayExpense(List<Expense> expenseList) {
        DefaultTableModel aModel = new DefaultTableModel() {

            //setting the jtable read only
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 7){
                    return true;
                }
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {

                    case 1:
                        return ImageIcon.class;
                    case 6:
                        return Date.class;
                    default:
                        return Object.class;
                }
            }

            ;
        };

        //setting the column name
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date",""};
        aModel.setColumnIdentifiers(headers);
        if (expenseList == null) {
            this.tableExpense.setModel(aModel);
            return;
        }

        int[] size = {25, 50, 100, 60, 110, 315, 120,100};
        Object[] objects = new Object[8];
        ListIterator<Expense> expenseListIterator = expenseList.listIterator();
        int i = 1;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int k = 0;
                for (int col : size) {
                    TableColumn column = tableExpense.getColumnModel().getColumn(k++);
                    column.setMinWidth(col);
                    column.setMaxWidth(col);
                    column.setPreferredWidth(col);
                }
            }
        });

        //populating the tablemodel
        while (expenseListIterator.hasNext()) {
            Expense expense = expenseListIterator.next();
            String categoryName = homeViewModel.getCategoryNameById(expense.getCategoryId());
            ImageIcon icon = new ImageIcon(getClass().getResource(Helper.getIconPathByCategoryName(categoryName)));
            objects[0] = i++;
            objects[1] = icon;
            objects[2] = categoryName;
            objects[3] = expense.getCurrency();
            objects[4] = expense.getCost();
            objects[5] = expense.getInfo();
            objects[6] = expense.getDateCreated();
            objects[7] = expense.getId();

            aModel.addRow(objects);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tableExpense.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
                tableExpense.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField()));
            }
        });

        //binding the jtable to the model
        this.tableExpense.setModel(aModel);
        scrollPane.setViewportView(tableExpense);
    }


    private void logout() {
        Helper.loggedInAccount = null;
        dispose();
        Helper.showScreen("Login");
    }

    class ButtonRenderer extends JButton implements TableCellRenderer
    {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
                                                       boolean selected, boolean focused, int row, int col) {

            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj==null) ? "":"Delete");

            return this;
        }

    }

    //BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor
    {
        protected JButton btn;
        private String lbl;
        private Boolean clicked;

        public ButtonEditor(JTextField txt) {
            super(txt);

            btn=new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    fireEditingStopped();
                }
            });
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                                                     boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl=(obj==null) ? "":obj.toString();
            btn.setText("Delete");
            clicked=true;
            homeViewModel.deleteSelected(Integer.parseInt(lbl));
            return btn;
        }

        //IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            //SET IT TO FALSE NOW THAT ITS CLICKED
            clicked=false;
            return new String("Delete");
        }

        @Override
        public boolean stopCellEditing() {

            //SET CLICKED TO FALSE FIRST
            clicked=false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }
    }

}
