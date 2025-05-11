package com.example.webhookapp.service;

import com.example.webhookapp.model.WebhookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {
    
    private final RestTemplate restTemplate;
    private final JwtService jwtService;
    private final SqlProblemSolver sqlProblemSolver;
    
    @Value("${webhook.generation.url}")
    private String webhookGenerationUrl;
    
    public WebhookService(JwtService jwtService, SqlProblemSolver sqlProblemSolver) {
        this.restTemplate = new RestTemplate();
        this.jwtService = jwtService;
        this.sqlProblemSolver = sqlProblemSolver;
    }
    
    public WebhookResponse generateWebhook() {
        // Send POST request to generate webhook
        return restTemplate.postForObject(webhookGenerationUrl, null, WebhookResponse.class);
    }
    
    public void sendSolution(String webhookUrl, String sqlSolution) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Generate JWT token with the solution
        Map<String, Object> claims = new HashMap<>();
        claims.put("solution", sqlSolution);
        String token = jwtService.generateToken(claims);
        
        // Add token to headers
        headers.set("Authorization", "Bearer " + token);
        
        // Create request entity with headers
        HttpEntity<String> entity = new HttpEntity<>(sqlSolution, headers);
        
        // Send solution to webhook URL
        restTemplate.postForObject(webhookUrl, entity, String.class);
    }
}
