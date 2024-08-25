package com.kodilla.frontend.views;

import com.kodilla.frontend.domain.Review;
import com.kodilla.frontend.service.ReviewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("reviews")
public class ReviewView extends VerticalLayout {

    private final ReviewService reviewService;

    private final Grid<Review> reviewGrid;

    public ReviewView(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
        this.reviewGrid = new Grid<>(Review.class);
        this.reviewGrid.setColumns("id", "rating", "content", "doctor", "patient");

        Button refreshButton = new Button("Refresh", event -> refreshReviews());

        add(refreshButton, reviewGrid);

        // Initial load
        refreshReviews();
    }

    private void refreshReviews() {
        reviewGrid.setItems(reviewService.getAllReviews());
    }
}
