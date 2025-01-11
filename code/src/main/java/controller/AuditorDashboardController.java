package controller;

import view.AuditorDashboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuditorDashboardController {

    AuditorDashboard view;
    public  AuditorDashboardController(AuditorDashboard view) {
        this.view = view;
        addAuditorDashboardEvents();
    }
    public void addAuditorDashboardEvents(){

        addLogoutEvent();

    }
    public  void addLogoutEvent(){
        this.view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle

                AccountSessionHandler.ClearCurrentAccountSession();

            }
        });
    }




}
