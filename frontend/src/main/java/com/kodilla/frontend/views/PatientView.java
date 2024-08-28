package com.kodilla.frontend.views;

import com.kodilla.frontend.MainView;
import com.kodilla.frontend.domain.dto.PatientDto;
import com.kodilla.frontend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.List;
@Route("patients")
public class PatientView extends VerticalLayout {
    private final PatientService patientService;
    private final Grid<PatientDto> patientGrid = new Grid<>(PatientDto.class);
    private final Dialog editorDialog = new Dialog();
    private PatientDto currentPatientDto;
    public PatientView(PatientService patientService) {
        this.patientService = patientService;
        configureGrid();
        updatePatientList();

        RouterLink backButton = new RouterLink("Back", MainView.class);
        backButton.getElement().getStyle().set("margin-right", "auto");

        HorizontalLayout buttonsLayout = new HorizontalLayout(backButton, createAddButton());

        add(buttonsLayout, patientGrid);
    }
    private void configureGrid() {
        patientGrid.setColumns("id", "firstName", "lastName", "email", "phone");
        patientGrid.addComponentColumn(patientDto -> {
            Button editButton = new Button("Edit", click -> openEditorDialog(patientDto));
            Button deleteButton = new Button("Delete", click -> deletePatient(patientDto));
            VerticalLayout buttonsLayout = new VerticalLayout(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions");
    }
    private Button createAddButton() {
        return new Button("Add Patient", click -> openEditorDialog(null));
    }
    private void openEditorDialog(PatientDto patientDto) {
        currentPatientDto = patientDto;
        editorDialog.removeAll();
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField emailField = new TextField("Email");
        TextField phoneField = new TextField("Phone");

        if (patientDto != null) {
            firstNameField.setValue(patientDto.getFirstName());
            lastNameField.setValue(patientDto.getLastName());
            emailField.setValue(patientDto.getEmail());
            phoneField.setValue(patientDto.getPhone());
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
        if (currentPatientDto == null) {
            PatientDto newPatientDto = new PatientDto();
            newPatientDto.setFirstName(firstName);
            newPatientDto.setLastName(lastName);
            newPatientDto.setEmail(email);
            newPatientDto.setPhone(phone);
            patientService.save(newPatientDto);
        } else {
            currentPatientDto.setFirstName(firstName);
            currentPatientDto.setLastName(lastName);
            currentPatientDto.setEmail(email);
            currentPatientDto.setPhone(phone);
            patientService.save(currentPatientDto);
        }
        updatePatientList();
        editorDialog.close();
    }
    private void deletePatient(PatientDto patientDto) {
        patientService.deleteById(patientDto.getId());
        updatePatientList();
    }
    private void updatePatientList() {
        List<PatientDto> patientDtos = patientService.findAll();
        patientGrid.setItems(patientDtos);
    }
}
