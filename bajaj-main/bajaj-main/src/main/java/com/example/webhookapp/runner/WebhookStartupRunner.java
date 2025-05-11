package com.example.webhookapp.runner;

import com.example.webhookapp.model.WebhookResponse;
import com.example.webhookapp.service.SqlProblemSolver;
import com.example.webhookapp.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebhookStartupRunner implements CommandLineRunner {
    
    private final WebhookService webhookService;
    private final SqlProblemSolver sqlProblemSolver;
    
    public WebhookStartupRunner(WebhookService webhookService, SqlProblemSolver sqlProblemSolver) {
        this.webhookService = webhookService;
        this.sqlProblemSolver = sqlProblemSolver;
    }
    
    @Override
    public void run(String... args) {
        // Step 1: Generate webhook
        WebhookResponse response = webhookService.generateWebhook();
        
        // Step 2: Setup test data and solve SQL problem
        sqlProblemSolver.setupTestData();
        String sqlSolution = sqlProblemSolver.solveSqlProblem(response.getSqlProblem());
        
        // Step 3: Send solution to webhook URL
        webhookService.sendSolution(response.getWebhookUrl(), sqlSolution);
    }
}