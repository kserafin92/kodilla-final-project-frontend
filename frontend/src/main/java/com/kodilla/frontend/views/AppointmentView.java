package com.kodilla.frontend.views;

import com.kodilla.frontend.MainView;
import com.kodilla.frontend.domain.dto.AppointmentDto;
import com.kodilla.frontend.domain.dto.DoctorDto;
import com.kodilla.frontend.domain.dto.PatientDto;
import com.kodilla.frontend.service.AppointmentService;
import com.kodilla.frontend.service.CurrencyService;
import com.kodilla.frontend.service.DoctorService;
import com.kodilla.frontend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.time.LocalDateTime;
import java.util.Map;

@Route("appointments")

public class AppointmentView extends VerticalLayout {
    private final AppointmentService appointmentService;
    private final CurrencyService currencyService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final Grid<AppointmentDto> appointmentGrid = new Grid<>(AppointmentDto.class);
    private final Dialog editorDialog = new Dialog();
    private AppointmentDto currentAppointment;
    public AppointmentView(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, CurrencyService currencyService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.currencyService = currencyService;
        configureGrid();
        updateAppointmentList();

        RouterLink backButton = new RouterLink("Back", MainView.class);
        backButton.getElement().getStyle().set("margin-right", "auto");

        HorizontalLayout buttonsLayout = new HorizontalLayout(backButton, createAddButton());

        add(buttonsLayout, appointmentGrid);
    }
    private void configureGrid() {
        appointmentGrid.removeAllColumns();
        appointmentGrid.addColumn(appointment -> appointment.getDateTime().toString()).setHeader("Date and Time");
        appointmentGrid.addColumn(appointment -> appointment.getDoctor().getLastName()).setHeader("Doctor");
        appointmentGrid.addColumn(appointment -> appointment.getPatient().getLastName()).setHeader("Patient");
        appointmentGrid.addColumn(appointment -> String.format("%.2f", appointment.getPricePln()))
                .setHeader("Price PLN");
        appointmentGrid.addColumn(appointment -> appointment.getPrice()).setHeader("Price");
        appointmentGrid.addColumn(appointment -> appointment.getCurrency()).setHeader("Currency");

        appointmentGrid.addComponentColumn(appointment -> {
            Button editButton = new Button("Edit", click -> openEditorDialog(appointment));
            Button deleteButton = new Button("Delete", click -> deleteAppointment(appointment));
            VerticalLayout buttonsLayout = new VerticalLayout(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions");
    }
    private Button createAddButton() {
        return new Button("Add Appointment", click -> openEditorDialog(null));
    }
    private void openEditorDialog(AppointmentDto appointment) {
        currentAppointment = appointment;
        editorDialog.removeAll();

        DateTimePicker dateTimePicker = new DateTimePicker("Date and Time");

        ComboBox<DoctorDto> doctorComboBox = new ComboBox<>("Doctor");
        doctorComboBox.setItemLabelGenerator(DoctorDto::getLastName);
        doctorComboBox.setItems(doctorService.findAll());

        ComboBox<PatientDto> patientComboBox = new ComboBox<>("Patient");
        patientComboBox.setItemLabelGenerator(PatientDto::getLastName);
        patientComboBox.setItems(patientService.findAll());

        ComboBox<String> currencyComboBox = new ComboBox<>("Currency");
        currencyComboBox.setLabel("currency");
        Map<String, Double> exchangeRate = currencyService.getExchangeRate();
        currencyComboBox.setItems(exchangeRate.keySet());

        NumberField price = new NumberField("price");
        price.setMin(1.0);

        if (appointment != null) {
            dateTimePicker.setValue(appointment.getDateTime());

            DoctorDto doctor = doctorService.findById(appointment.getDoctor().getId());
            if (doctor != null) {
                doctorComboBox.setValue(doctor);
            }

            PatientDto patient = patientService.findById(appointment.getPatient().getId());
            if (patient != null) {
                patientComboBox.setValue(patient);
            }

            price.setValue(appointment.getPrice());

            currencyComboBox.setValue((appointment.getCurrency()));
        }

        Button saveButton = new Button("Save", click -> saveAppointment(
                dateTimePicker.getValue(),
                doctorComboBox.getValue(),
                patientComboBox.getValue(),
                currencyComboBox.getValue(),
                price.getValue(),
                exchangeRate.get(currencyComboBox.getValue()) * price.getValue()
        ));
        Button cancelButton = new Button("Cancel", click -> editorDialog.close());

        editorDialog.add(dateTimePicker, doctorComboBox, patientComboBox, price, currencyComboBox, saveButton, cancelButton);
        editorDialog.open();
    }
    private void saveAppointment(LocalDateTime dateTime, DoctorDto doctor, PatientDto patient, String currency, Double price, Double pricePln) {
        if (currentAppointment == null) {
            AppointmentDto newAppointment = AppointmentDto.builder()
                    .dateTime(dateTime)
                    .doctor(doctor)
                    .patient(patient)
                    .pricePln(pricePln)
                    .price(price)
                    .currency(currency)
                    .build();
            appointmentService.save(newAppointment);
        } else {
            currentAppointment.setDateTime(dateTime);
            currentAppointment.setDoctor(doctor);
            currentAppointment.setPatient(patient);
            currentAppointment.setPricePln(pricePln);
            currentAppointment.setPrice(price);
            currentAppointment.setCurrency(currency);
            appointmentService.save(currentAppointment);
        }
        updateAppointmentList();
        editorDialog.close();
    }
    private void deleteAppointment(AppointmentDto appointment) {
        appointmentService.deleteById(appointment.getId());
        updateAppointmentList();
    }
    private void updateAppointmentList() {
        appointmentGrid.setItems(appointmentService.findAll());
    }
}
