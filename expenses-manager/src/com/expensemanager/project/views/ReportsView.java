package com.expensemanager.project.views;

import com.expensemanager.project.classes.Expense;
import com.expensemanager.project.helpers.Helper;
import com.expensemanager.project.helpers.pdf.PdfCreator;
import com.expensemanager.project.helpers.pdf.PdfDTO;
import com.expensemanager.project.interfaces.IView;
import com.expensemanager.project.interfaces.IViewModel;
import com.expensemanager.project.interfaces.Report.IReportViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class ReportsView extends JFrame implements IView {
    private IReportViewModel viewModel;
    private JFileChooser fileChooser;
    private JPanel panelNorth, panelCenter,panelSouth;
    private JLabel labelFromDate, labelToDate, labelInstruction,labelSumOfExpenses,labelExpensesTotal;
    private JTextField tfFromDate, tfToDate;
    private JButton btnCancel, btnGetReport,btnSaveAsPdf;
    private JComboBox comboBoxCurrencies;
    private JTable tableExpenses;
    private JScrollPane scrollPane;
    private GridBagConstraints gridBagConstraints;
    private String currentCurrency = "ILS";
    List<Expense> expensesList = new LinkedList<>();


    @Override
    public void setViewModel(IViewModel viewModel) {
        if (viewModel instanceof IReportViewModel)
            this.viewModel = (IReportViewModel) viewModel;
    }

    @Override
    public void showMessage(String msg) {
        Helper.showMessage("Reports", msg);
    }

    @Override
    public void init() {
        scrollPane = new JScrollPane();
        tableExpenses = new JTable();
        panelNorth = new JPanel(new GridBagLayout());
        panelCenter = new JPanel(new BorderLayout());
        panelSouth = new JPanel(new FlowLayout());
        labelFromDate = new JLabel("From Date: ");
        labelToDate = new JLabel("To Date: ");
        labelSumOfExpenses = new JLabel("The Sum Of Selected Expenses: ");
        labelExpensesTotal = new JLabel("0");
        comboBoxCurrencies = new JComboBox();
        tfFromDate = new JTextField(15);
        tfToDate = new JTextField(15);
        labelInstruction = new JLabel("<html><font color='red'>Valid date format: DD-MM-YYYY<br><br>Minimum year: 2021<br>Max year: 2100</font></html>");
        btnCancel = new JButton("Cancel");
        btnGetReport = new JButton("Get Report");
        gridBagConstraints = new GridBagConstraints();
        btnSaveAsPdf = new JButton("Save Report As PDF");
        fileChooser = new JFileChooser();


    }

    @Override
    public void start() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setDialogTitle("select path to save file");


        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelNorth.add(labelFromDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(tfFromDate, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridheight = 3;
        panelNorth.add(labelInstruction, gridBagConstraints);

        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelNorth.add(labelToDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(tfToDate, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelNorth.add(btnGetReport, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(btnCancel, gridBagConstraints);


        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));



        setComboBoxCurrencies();
        btnSaveAsPdf.addActionListener(e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    JButton b = (JButton) e.getSource();
                    int userSelection = fileChooser.showSaveDialog(b.getParent());

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String[][] strData = saveDataAssStrDoubleArr();
                        PdfDTO pdfDTO = new PdfDTO();
                        pdfDTO.setFrom(tfFromDate.getText());
                        pdfDTO.setTo(tfToDate.getText());
                        pdfDTO.setSum(Double.parseDouble(String.format("%.2f",Double.parseDouble(labelExpensesTotal.getText()))));
                        PdfCreator pdfCreator = new PdfCreator();
                        pdfCreator.createPdfFile(fileToSave.getPath(), strData,pdfDTO);

                        Helper.showMessage("Report", "Report Saved");
                    }

                }
            });
        });

        panelSouth.add(labelSumOfExpenses);
        panelSouth.add(labelExpensesTotal);
        panelSouth.add(comboBoxCurrencies);
        panelSouth.add(btnSaveAsPdf);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth,BorderLayout.SOUTH);


        setLocationRelativeTo(null);

        this.setMinimumSize(new Dimension(800, 400));


        btnGetReport.addActionListener(e -> {
            String fromDate = tfFromDate.getText();
            String toDate = tfToDate.getText();
            viewModel.getReport(fromDate, toDate);
        });

        btnCancel.addActionListener(e -> showHomeScreen());
    }

    private String[][] saveDataAssStrDoubleArr() {
        String[][] data = new String[expensesList.size()][];

        int i = 0;
        for (Expense expense : expensesList) {
            data[i] = new String[5];
            data[i][0] = expense.getCategoryName();
            data[i][1] = expense.getCurrency();
            data[i][2] = String.valueOf(expense.getCost());
            data[i][3] = expense.getInfo();
            data[i][4] = expense.getDateCreated().toString();

            i++;
        }

        return data;
    }


    public void displayExpense(List<Expense> expenseList) {
        this.expensesList = expenseList;
        DefaultTableModel aModel = new DefaultTableModel() {

            //setting the jtable read only
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 1 -> ImageIcon.class;
                    case 6 -> Date.class;
                    default -> Object.class;
                };
            }

        };

        //setting the column name
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date"};
        aModel.setColumnIdentifiers(headers);
        this.tableExpenses.setModel(aModel);
        if (expenseList == null) {
            return;
        }

        Object[] objects = new Object[7];
        ListIterator<Expense> expenseListIterator = expenseList.listIterator();
        int[] size = {25, 50, 100, 60, 110, 315, 120};
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int k = 0;
                for (int col : size) {
                    TableColumn column = tableExpenses.getColumnModel().getColumn(k++);
                    column.setMinWidth(col);
                    column.setMaxWidth(col);
                    column.setPreferredWidth(col);
                }
            }
        });


        int i = 1;

        double totalSum = 0;
        //populating the tablemodel
        while (expenseListIterator.hasNext()) {
            Expense expense = expenseListIterator.next();
            String categoryName = expense.getCategoryName();
            String path = Helper.getIconPathByCategoryName(categoryName);
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
            float cost = expense.getCost();
            String curr = expense.getCurrency();
            objects[0] = i++;
            objects[1] = icon;
            objects[2] = categoryName;
            objects[3] = curr;
            objects[4] = cost;
            objects[5] = expense.getInfo();
            objects[6] = expense.getDateCreated();

            totalSum+=cost*calcCurrencyRate(curr);
            aModel.addRow(objects);
        }

        labelExpensesTotal.setText(String.format("%.2f",totalSum));
        //binding the jtable to the model
        this.tableExpenses.setModel(aModel);
        scrollPane.setViewportView(tableExpenses);
    }

    private void showHomeScreen() {
        dispose();
        Helper.showScreen("Home");
    }

    private void setComboBoxCurrencies(){
        for(Map.Entry set:Helper.currencies.entrySet()){
            comboBoxCurrencies.addItem(set.getKey());
        }
    }

    private double calcCurrencyRate(String currency){
        switch (comboBoxCurrencies.getSelectedItem().toString()){
            case "ILS":
                return Helper.currencies.get(currency);
            case "USD":
                if(currency.equals("USD")) return 1.0;
                else if(currency.equals("ILS")) return 0.32;
                else return 1.13;
            case "EURO":
                if(currency.equals("EURO")) return 1.0;
                else if(currency.equals("USD")) return 0.89;
                else return 0.28;

        }
        return 0;
    }


}
