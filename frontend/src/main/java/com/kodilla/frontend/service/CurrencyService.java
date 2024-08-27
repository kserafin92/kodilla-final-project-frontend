package com.kodilla.frontend.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {
    private static final String BASE_URL = "http://localhost:8081/currency-rates";
    private final RestTemplate restTemplate;
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Map<String, Double> getExchangeRate() {
        ParameterizedTypeReference<Map<String, Double>> responseType = new ParameterizedTypeReference<Map<String, Double>>() {
        };
        ResponseEntity<Map<String, Double>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }
}


