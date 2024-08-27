package com.kodilla.frontend.views;

import com.kodilla.frontend.domain.dto.ReviewDto;
import com.kodilla.frontend.domain.dto.DoctorDto;
import com.kodilla.frontend.domain.dto.PatientDto;
import com.kodilla.frontend.service.ReviewService;
import com.kodilla.frontend.service.DoctorService;
import com.kodilla.frontend.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("reviews")
public class ReviewView extends VerticalLayout {

    private final ReviewService reviewService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    private final Grid<ReviewDto> reviewGrid = new Grid<>(ReviewDto.class);
    private final Dialog editorDialog = new Dialog();
    private ReviewDto currentReview;

    @Autowired
    public ReviewView(ReviewService reviewService, DoctorService doctorService, PatientService patientService) {
        this.reviewService = reviewService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        configureGrid();
        updateReviewList();
        add(createAddButton(), reviewGrid);
    }

    private void configureGrid() {
        reviewGrid.removeAllColumns();  // Clear existing columns

        reviewGrid.addColumn(ReviewDto::getRating).setHeader("Rating");
        reviewGrid.addColumn(ReviewDto::getContent).setHeader("Content");
        reviewGrid.addColumn(ReviewDto::getDoctorLastName).setHeader("Doctor Last Name");
        reviewGrid.addColumn(ReviewDto::getPatientLastName).setHeader("Patient Last Name");

        reviewGrid.addComponentColumn(review -> {
            Button editButton = new Button("Edit", click -> openEditorDialog(review));
            Button deleteButton = new Button("Delete", click -> deleteReview(review));
            VerticalLayout buttonsLayout = new VerticalLayout(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions");
    }

    private Button createAddButton() {
        return new Button("Add Review", click -> openEditorDialog(null));
    }

    private void openEditorDialog(ReviewDto review) {
        currentReview = review;
        editorDialog.removeAll();
        TextField ratingField = new TextField("Rating");
        TextField contentField = new TextField("Content");

        ComboBox<DoctorDto> doctorComboBox = new ComboBox<>("Doctor");
        doctorComboBox.setItems(doctorService.findAll());
        doctorComboBox.setItemLabelGenerator(doctor -> doctor.getLastName());

        ComboBox<PatientDto> patientComboBox = new ComboBox<>("Patient");
        patientComboBox.setItems(patientService.findAll());
        patientComboBox.setItemLabelGenerator(patient -> patient.getLastName());

        if (review != null) {
            ratingField.setValue(String.valueOf(review.getRating()));
            contentField.setValue(review.getContent());
            doctorComboBox.setValue(doctorService.findById(review.getDoctorId()));
            patientComboBox.setValue(patientService.findById(review.getPatientId()));
        }

        Button saveButton = new Button("Save", click -> saveReview(
                Integer.parseInt(ratingField.getValue()),
                contentField.getValue(),
                doctorComboBox.getValue().getId(),
                patientComboBox.getValue().getId()
        ));
        Button cancelButton = new Button("Cancel", click -> editorDialog.close());

        editorDialog.add(ratingField, contentField, doctorComboBox, patientComboBox, saveButton, cancelButton);
        editorDialog.open();
    }


    private void saveReview(int rating, String content, Long doctorId, Long patientId) {
        if (currentReview == null) {
            ReviewDto newReview = new ReviewDto();
            newReview.setRating(rating);
            newReview.setContent(content);
            newReview.setDoctorId(doctorId);
            newReview.setPatientId(patientId);
            reviewService.save(newReview);
        } else {
            currentReview.setRating(rating);
            currentReview.setContent(content);
            currentReview.setDoctorId(doctorId);
            currentReview.setPatientId(patientId);
            reviewService.save(currentReview);
        }
        updateReviewList();
        editorDialog.close();
    }

    private void deleteReview(ReviewDto review) {
        reviewService.deleteById(review.getId());
        updateReviewList();
    }

    private void updateReviewList() {
        reviewGrid.setItems(reviewService.findAll());
    }
}
