package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.Review;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class ReviewService {

    private final WebClient webClient;

    public ReviewService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/reviews")
                .build();
    }

    public List<Review> getAllReviews() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }

    public Review getReviewById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Review.class)
                .block();
    }

    public Review createReview(Review review) {
        return webClient.post()
                .bodyValue(review)
                .retrieve()
                .bodyToMono(Review.class)
                .block();
    }

    public Review updateReview(Long id, Review review) {
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(review)
                .retrieve()
                .bodyToMono(Review.class)
                .block();
    }

    public void deleteReview(Long id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}

