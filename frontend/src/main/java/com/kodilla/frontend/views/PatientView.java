package com.kodilla.frontend.views;

import com.kodilla.frontend.dto.PatientDto;
import com.kodilla.frontend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

@Route("patients")
public class PatientView extends VerticalLayout {

    private final PatientService patientService;
    private final Grid<PatientDto> patientGrid = new Grid<>(PatientDto.class);
    private final Dialog editorDialog = new Dialog();
    private PatientDto currentPatient;

    public PatientView(PatientService patientService) {
        this.patientService = patientService;
        setSizeFull();
        configureGrid();
        add(patientGrid, createAddButton());
        updatePatientList();
    }

    private void configureGrid() {
        patientGrid.setColumns("id", "firstName", "lastName", "email", "phone");

        patientGrid.addComponentColumn(patient -> {
            Button editButton = new Button("Edit", click -> openEditorDialog(patient));
            Button deleteButton = new Button("Delete", click -> deletePatient(patient));
            VerticalLayout buttonsLayout = new VerticalLayout(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions");
    }

    private Button createAddButton() {
        return new Button("Add Patient", click -> openEditorDialog(null));
    }

    private void openEditorDialog(PatientDto patient) {
        currentPatient = patient;
        editorDialog.removeAll();
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField emailField = new TextField("Email");
        TextField phoneField = new TextField("Phone");

        if (patient != null) {
            firstNameField.setValue(patient.getFirstName());
            lastNameField.setValue(patient.getLastName());
            emailField.setValue(patient.getEmail());
            phoneField.setValue(patient.getPhone());
        }

        Button saveButton = new Button("Save", click -> savePatient(
                firstNameField.getValue(),
                lastNameField.getValue(),
                emailField.getValue(),
                phoneField.getValue()
        ));
        Button cancelButton = new Button("Cancel", click -> editorDialog.close());

        editorDialog.add(firstNameField, lastNameField, emailField, phoneField, saveButton, cancelButton);
        editorDialog.open();
    }

    private void savePatient(String firstName, String lastName, String email, String phone) {
        if (currentPatient == null) {
            PatientDto newPatient = PatientDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone)
                    .build();
            patientService.save(newPatient);
        } else {
            currentPatient.setFirstName(firstName);
            currentPatient.setLastName(lastName);
            currentPatient.setEmail(email);
            currentPatient.setPhone(phone);
            patientService.save(currentPatient);
        }
        updatePatientList();
        editorDialog.close();
    }

    private void deletePatient(PatientDto patient) {
        patientService.deleteById(patient.getId());
        updatePatientList();
    }

    private void updatePatientList() {
        patientGrid.setItems(patientService.findAll());
    }
}
