package com.kodilla.frontend.views;

import com.kodilla.frontend.domain.Appointment;
import com.kodilla.frontend.service.AppointmentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("appointments")
public class AppointmentView extends VerticalLayout {

    private final AppointmentService appointmentService;

    private final Grid<Appointment> appointmentGrid;

    public AppointmentView(@Autowired AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.appointmentGrid = new Grid<>(Appointment.class);
        this.appointmentGrid.setColumns("id", "dateTime", "doctor", "patient");

        Button refreshButton = new Button("Refresh", event -> refreshAppointments());

        add(refreshButton, appointmentGrid);

        // Initial load
        refreshAppointments();
    }

    private void refreshAppointments() {
        appointmentGrid.setItems(appointmentService.getAllAppointments());
    }
}
