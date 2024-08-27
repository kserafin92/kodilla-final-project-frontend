package com.kodilla.frontend;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
        Button doctorButton = new Button("Doctors", e -> getUI().ifPresent(ui -> ui.navigate("doctors")));
        Button patientButton = new Button("Patients", e -> getUI().ifPresent(ui -> ui.navigate("patients")));
        Button appointmentButton = new Button("Appointments", e -> getUI().ifPresent(ui -> ui.navigate("appointments")));
        Button reviewButton = new Button("Reviews", e -> getUI().ifPresent(ui -> ui.navigate("reviews")));
        add(doctorButton, patientButton, appointmentButton, reviewButton);
    }
}
