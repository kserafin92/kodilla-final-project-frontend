package com.kodilla.frontend.views;

import com.kodilla.frontend.domain.Patient;
import com.kodilla.frontend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("patients")
public class PatientView extends VerticalLayout {

    private final PatientService patientService;

    private final Grid<Patient> patientGrid;

    public PatientView(@Autowired PatientService patientService) {
        this.patientService = patientService;
        this.patientGrid = new Grid<>(Patient.class);
        this.patientGrid.setColumns("id", "firstName", "lastName", "email");

        Button refreshButton = new Button("Refresh", event -> refreshPatients());

        add(refreshButton, patientGrid);

        // Initial load
        refreshPatients();
    }

    private void refreshPatients() {
        patientGrid.setItems(patientService.getAllPatients());
    }
}
