package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.ClauseTabController;
import model.SystemManagement.Standard.Clause;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class ClauseTab extends JPanel {

    private JButton createButton = new JButton("Create New Clause");
    private ButtonRenderer buttonRenderer = new ButtonRenderer();
    private List<Clause> data = ControllersGetter.organizationsController.getAllClauses();
    private ClauseTabController clauseTabController;
    private static String[] columnNamesCreateEdit = {"IdOrganization", "IdManagementSystem", "IdStandard", "Name", "Description", "Reference"};
    DefaultTableModel model;
    JTable clauseTable;

    public ClauseTab() {
        clauseTabController = new ClauseTabController(this);
        setUpUi();
    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditButton() {
        return buttonRenderer.getEditButton();
    }

    public JButton getDeleteButton() {
        return buttonRenderer.getDeleteButton();
    }

    private void setUpUi() {
        // Set the layout manager for the panel
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(236, 240, 241)); // Light gray background
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a button panel at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241)); // Light gray background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add "Create" button
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(new Color(52, 152, 219)); // Blue color
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(createButton);
//        edit

        // Add the button panel to the top of the tab
        this.add(buttonPanel, BorderLayout.NORTH);

        // Define column names
        String[] columnNames = {"IdClause", "IdOrganization", "IdManagementSystem", "IdStandard", "Name", "Description", "Reference", "Actions"};

        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);

        // Create and return the table model
        model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 7) is editable {accept event}
                return column == 7;
            }
        };

        clauseTable = new JTable(model);
        clauseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clauseTable.setRowHeight(30);
        clauseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        clauseTable.getTableHeader().setBackground(new Color(98, 78, 136)); // Dark blue header
        clauseTable.getTableHeader().setForeground(Color.WHITE);
        clauseTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = clauseTable.getColumnModel().getColumn(7);
        actionsColumn.setCellRenderer(buttonRenderer);
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), clauseTable, clauseTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(clauseTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getAllClauses();
        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Clause clause : data) {
            Object[] rowData = {
                    clause.getIdClause(),
                    clause.getIdOrganization(),
                    clause.getIdManagementSystem(),
                    clause.getIdStandard(),
                    clause.getName(),
                    clause.getDescription(),
                    clause.getReference(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = clauseTable.getColumnModel().getColumn(7);
        clauseTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(7);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), clauseTable, clauseTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        clauseTable.addColumn(actionsColumn);

        // Repaint the table to reflect the changes
        clauseTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the ClauseTab
        JFrame frame = new JFrame("Clauses Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the ClauseTab panel to the frame
        ClauseTab clauseTab = new ClauseTab();
        frame.add(clauseTab);

        // Display the frame
        frame.setVisible(true);
    }
}