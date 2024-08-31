package com.kodilla.frontend.service;

import com.kodilla.frontend.domain.dto.ReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class ReviewService {
    private static final String BASE_URL = "http://localhost:8081/reviews";
    private final RestTemplate restTemplate;
    public ReviewService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Collection<ReviewDto> findAll() {
        return Arrays.stream(restTemplate.getForObject(BASE_URL, ReviewDto[].class))
                .collect(Collectors.toList());
    }
    public ReviewDto findById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, ReviewDto.class);
    }
    public ReviewDto save(ReviewDto review) {
        try {
            if (review.getId() == null) {
                return restTemplate.postForObject(BASE_URL, review, ReviewDto.class);
            } else {
                restTemplate.put(BASE_URL + "/" + review.getId(), review);
                return review;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save review", e);
        }
    }
    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
