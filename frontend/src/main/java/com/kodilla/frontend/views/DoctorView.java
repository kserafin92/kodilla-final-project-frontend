package com.kodilla.frontend.views;

import com.kodilla.frontend.MainView;
import com.kodilla.frontend.domain.dto.DoctorDto;
import com.kodilla.frontend.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("doctors")
public class DoctorView extends VerticalLayout {
    private final DoctorService doctorService;
    private final Grid<DoctorDto> doctorGrid = new Grid<>(DoctorDto.class);
    private final Dialog editorDialog = new Dialog();
    private DoctorDto currentDoctor;
    public DoctorView(DoctorService doctorService) {
        this.doctorService = doctorService;
        configureGrid();
        updateDoctorList();

        RouterLink backButton = new RouterLink("Back", MainView.class);
        backButton.getElement().getStyle().set("margin-right", "auto");

        HorizontalLayout buttonsLayout = new HorizontalLayout(backButton, createAddButton());

        add(buttonsLayout, doctorGrid);
    }
    private void configureGrid() {
        doctorGrid.setColumns("id", "firstName", "lastName", "specialization");
        doctorGrid.addComponentColumn(doctor -> {
            Button editButton = new Button("Edit", click -> openEditorDialog(doctor));
            Button deleteButton = new Button("Delete", click -> deleteDoctor(doctor));
            VerticalLayout buttonsLayout = new VerticalLayout(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions");
    }
    private Button createAddButton() {
        return new Button("Add Doctor", click -> openEditorDialog(null));
    }
    private void openEditorDialog(DoctorDto doctor) {
        currentDoctor = doctor;
        editorDialog.removeAll();
        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        TextField specializationField = new TextField("Specialization");

        if (doctor != null) {
            firstNameField.setValue(doctor.getFirstName());
            lastNameField.setValue(doctor.getLastName());
            specializationField.setValue(doctor.getSpecialization());
        }

        Button saveButton = new Button("Save", click -> saveDoctor(
                firstNameField.getValue(),
                lastNameField.getValue(),
                specializationField.getValue()
        ));
        Button cancelButton = new Button("Cancel", click -> editorDialog.close());

        editorDialog.add(firstNameField, lastNameField, specializationField, saveButton, cancelButton);
        editorDialog.open();
    }
    private void saveDoctor(String firstName, String lastName, String specialization) {
        if (currentDoctor == null) {
            DoctorDto newDoctor = DoctorDto.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .specialization(specialization)
                    .build();
            doctorService.save(newDoctor);
        } else {
            currentDoctor.setFirstName(firstName);
            currentDoctor.setLastName(lastName);
            currentDoctor.setSpecialization(specialization);
            doctorService.save(currentDoctor);
        }
        updateDoctorList();
        editorDialog.close();
    }
    private void deleteDoctor(DoctorDto doctor) {
        doctorService.deleteById(doctor.getId());
        updateDoctorList();
    }
    private void updateDoctorList() {
        doctorGrid.setItems(doctorService.findAll());
    }
}
