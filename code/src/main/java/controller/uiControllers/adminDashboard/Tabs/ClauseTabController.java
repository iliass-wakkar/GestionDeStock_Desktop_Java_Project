package controller.uiControllers.adminDashboard.Tabs;

import model.SystemManagement.Standard.Clause;
import utils.ControllersGetter;
import utils.SaveUtil;
import utils.interfaces.IButtonEditorEventsHandler;
import utils.interfaces.IFormDialogEventHandler;
import utils.interfaces.objectConverter.ClauseConverter;
import view.pages.AdminDashboard.ButtonEditor;
import view.pages.AdminDashboard.FormDialog;
import view.pages.AdminDashboard.ClauseTab;

import javax.swing.*;

public class ClauseTabController {
    private ClauseTab view;
    private FormDialog createClauseForm;
    private FormDialog editClauseForm;
    private String[] columnNames = ClauseTab.getColumnNamesCreateEdit();
    private SaveUtil<Clause> saveUtil = new SaveUtil(new ClauseConverter());

    public ClauseTabController(ClauseTab view) {
        this.view = view;
        initController();
    }

    private void initController() {
        addCreateClauseButtonEvent();
    }

    private void addCreateClauseButtonEvent() {
        view.getCreateButton().addActionListener(ActionEvent -> {
            createClauseForm = new FormDialog("Create Clause", columnNames, saveCreateClauseIFormEventHandler);
        });
    }

    private IFormDialogEventHandler saveEditClauseIFormEventHandler = (formDialog) -> {
        try {
            if (formDialog.validateForm()) {
                Clause clause = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = clause.getIdOrganization(); // Get the organization ID
                String idManagementSystem = clause.getIdManagementSystem(); // Get the management system ID
                String idStandard = clause.getIdStandard(); // Get the standard ID
                ControllersGetter.organizationsController.editSystemManagementStandardClauseById(idOrg, idManagementSystem, idStandard, formDialog.getId(), clause);
                view.refreshTable();
                JOptionPane.showMessageDialog(
                        editClauseForm,
                        "Clause updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                formDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    editClauseForm,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IFormDialogEventHandler saveCreateClauseIFormEventHandler = (formDialog) -> {
        System.out.println("ClauseTabController saveCreateClauseIFormEventHandler");
        try {
            if (formDialog.validateForm()) {
                Clause clause = saveUtil.saveFormData(formDialog.getFormData());
                String idOrg = clause.getIdOrganization(); // Get the organization ID
                String idManagementSystem = clause.getIdManagementSystem(); // Get the management system ID
                String idStandard = clause.getIdStandard(); // Get the standard ID
                ControllersGetter.organizationsController.createSystemManagementStandardClause(idOrg, idManagementSystem, idStandard, clause);
                JOptionPane.showMessageDialog(
                        formDialog,
                        "New Clause added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                view.refreshTable();
                formDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        formDialog,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    formDialog,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    };

    private IButtonEditorEventsHandler iButtonEditorEventsHandler = new IButtonEditorEventsHandler() {
        @Override
        public void editObjectEventHandler(ButtonEditor view) {
            String[] columnNames = ClauseTab.getColumnNamesCreateEdit();
            editClauseForm = new FormDialog("Edit", columnNames, view.getRowData(), saveEditClauseIFormEventHandler, view.getId());
        }
//edit
        @Override
        public void deleteObjectEventHandler(ButtonEditor buttonEditorView) {
            try {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this Clause?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    String idOrg = buttonEditorView.getRowData()[0].toString(); // Get the organization ID
                    String idManagementSystem = buttonEditorView.getRowData()[1].toString(); // Get the management system ID
                    String idStandard = buttonEditorView.getRowData()[2].toString(); // Get the standard ID
                    ControllersGetter.organizationsController.deleteSystemManagementStandardClauseById(idOrg, idManagementSystem, idStandard, buttonEditorView.getId());
                    view.refreshTable();
                    System.out.println("Deleting Clause");
                } else {
                    System.out.println("Deleting operation canceled.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "An error occurred: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    };

    public IButtonEditorEventsHandler getIButtonEditorEventsHandler() {
        return iButtonEditorEventsHandler;
    }
}