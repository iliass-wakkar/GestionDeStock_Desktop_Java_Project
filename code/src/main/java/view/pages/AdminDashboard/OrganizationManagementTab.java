package view.pages.AdminDashboard;

import controller.uiControllers.adminDashboard.Tabs.OrganizationManagementTabController;
import model.Organization.Organization;
import utils.TableConverterUtility;
import utils.ControllersGetter;
import view.components.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class OrganizationManagementTab extends JPanel {

    private JButton createButton =new JButton("Create New Organization");
    private ButtonRenderer buttonRenderer= new ButtonRenderer();
    private List<Organization> data = ControllersGetter.organizationsController.getOrganizations();
    private OrganizationManagementTabController organizationManagementTabController;
    private static String[] columnNamesCreateEdit = { "Name", "Description"};
    DefaultTableModel model;
    JTable auditTable;

    public OrganizationManagementTab() {
        organizationManagementTabController = new OrganizationManagementTabController(this);
        setUpUi();

    }

    public static String[] getColumnNamesCreateEdit() {
        return columnNamesCreateEdit;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getEditeButton() {
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

        // Add the button panel to the top of the tab
        this.add(buttonPanel, BorderLayout.NORTH);

        // Define column names
        String[] columnNames = {"IdOrganization", "Name", "Description","Actions"};


        Object[][] tableData = TableConverterUtility.convertToTableData(data, columnNames);


        // Create and return the table model
         model = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Actions" column (column index 4) is editable {accept event}
                return column == 3;
            }
        };

         auditTable = new JTable(model);
        auditTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        auditTable.setRowHeight(30);
        auditTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        auditTable.getTableHeader().setBackground(new Color(98, 78, 136)); // Dark blue header
        auditTable.getTableHeader().setForeground(Color.WHITE);
        auditTable.setFillsViewportHeight(true);

        // Add action buttons (Edit and Delete) to each row
        TableColumn actionsColumn = auditTable.getColumnModel().getColumn(3);
        actionsColumn.setCellRenderer(buttonRenderer);
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), auditTable, organizationManagementTabController.getIButtonEditorEventsHandler()));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(auditTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        // Fetch the latest data
        data = ControllersGetter.organizationsController.getOrganizations();
        System.out.println(data);
        // Clear the existing table data
        model.setRowCount(0);

        // Add the new data to the table
        for (Organization organization : data) {
            Object[] rowData = {
                    organization.getIdOrganization(),
                    organization.getName(),
                    organization.getDescription(),
                    "Actions" // Placeholder for the action buttons
            };
            model.addRow(rowData);
        }

        TableColumn actionsColumn = auditTable.getColumnModel().getColumn(3);
        auditTable.removeColumn(actionsColumn);

        // Recreate the "Actions" column with a new ButtonRenderer and ButtonEditor
        actionsColumn = new TableColumn(3);
        actionsColumn.setHeaderValue("Actions");
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox(), auditTable,organizationManagementTabController.getIButtonEditorEventsHandler()));

        // Re-add the "Actions" column to the table
        auditTable.addColumn(actionsColumn);


        // Repaint the table to reflect the changes
        auditTable.repaint();
    }

    public static void main(String[] args) {
        // Create a JFrame to display the AuditorManagementTab
        JFrame frame = new JFrame("Organizations Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null); // Center the frame

        // Add the AuditorManagementTab panel to the frame
        OrganizationManagementTab organizationManagementTab = new OrganizationManagementTab();
        frame.add(organizationManagementTab);

        // Display the frame
        frame.setVisible(true);
    }
}