package com.example.webhookapp.model;

public class WebhookResponse {
    private String webhookUrl;
    private String sqlProblem;
    
    // Getters and setters
    public String getWebhookUrl() {
        return webhookUrl;
    }
    
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
    
    public String getSqlProblem() {
        return sqlProblem;
    }
    
    public void setSqlProblem(String sqlProblem) {
        this.sqlProblem = sqlProblem;
    }
}