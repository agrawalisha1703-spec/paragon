package io.paragon.cli.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock LLM provider that returns deterministic, pre-defined responses.
 * Used for testing without calling real LLM APIs.
 * Perfect for CI/CD pipelines and cost-free testing.
 */
public class MockProvider implements LlmProvider {
    private final Map<String, String> responses;

    public MockProvider() {
        this.responses = new HashMap<>();
        initializeMockResponses();
    }

    /**
     * Initialize with sample mock responses for common prompts.
     * Deterministic: same prompt → same response every time.
     */
    private void initializeMockResponses() {
        // JSON responses
        responses.put("Generate a JSON invoice with fields: id, total, date",
                "{\"id\": \"INV-001\", \"total\": 99.99, \"date\": \"2026-11-14\"}");
        
        responses.put("Generate invoice JSON with fields: id, total, date",
                "{\"id\": \"INV-001\", \"total\": 99.99, \"date\": \"2026-11-14\"}");
        
        responses.put("Generate a greeting as JSON with fields: message, timestamp",
                "{\"message\": \"Hello, World!\", \"timestamp\": \"2026-11-14T10:30:00Z\"}");
        
        responses.put("Generate a greeting message as JSON with fields: message, timestamp",
                "{\"message\": \"Hello, World!\", \"timestamp\": \"2026-11-14T10:30:00Z\"}");

        // Text responses
        responses.put("Say hello to the user",
                "Hello! Welcome to Paragon. How can I assist you today?");
        
        responses.put("Generate a short greeting",
                "Hello, there!");
        
        responses.put("Explain machine learning in one sentence",
                "Machine learning is a field of artificial intelligence that enables systems to learn and improve from experience without being explicitly programmed.");

        // Email responses
        responses.put("Generate a professional email subject line for a job rejection",
                "We appreciate your interest - Application Status Update");
        
        responses.put("Write a professional email subject for a meeting reminder",
                "Reminder: Team Standup Meeting Tomorrow at 10 AM");

        // Data responses
        responses.put("Generate a CSV row with columns: name, age, city",
                "John Doe,30,San Francisco");
        
        responses.put("Generate a product description for a laptop",
                "High-performance laptop with 16GB RAM, 512GB SSD, Intel i7 processor, perfect for development and creative work.");
    }

    @Override
    public String call(String prompt) throws Exception {
        // Try exact match first
        if (responses.containsKey(prompt)) {
            return responses.get(prompt);
        }

        // Try partial match (useful for similar prompts)
        String sanitizedPrompt = prompt.toLowerCase().trim();
        for (Map.Entry<String, String> entry : responses.entrySet()) {
            if (entry.getKey().toLowerCase().contains(sanitizedPrompt) ||
                sanitizedPrompt.contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }

        // Fallback: return a generic response based on prompt keywords
        return generateFallbackResponse(prompt);
    }

    /**
     * Generate a fallback response based on prompt content.
     * This allows testing with custom prompts without pre-defining all responses.
     */
    private String generateFallbackResponse(String prompt) {
        if (prompt.toLowerCase().contains("json")) {
            return "{\"status\": \"success\", \"message\": \"Generated JSON response\"}";
        } else if (prompt.toLowerCase().contains("csv") || prompt.toLowerCase().contains("row")) {
            return "field1,field2,field3";
        } else if (prompt.toLowerCase().contains("email")) {
            return "Subject: Generic Email Response";
        } else if (prompt.toLowerCase().contains("hello") || prompt.toLowerCase().contains("greeting")) {
            return "Hello! This is a mock response.";
        } else {
            return "This is a mock LLM response for: " + prompt;
        }
    }

    /**
     * Allows manual registration of custom mock responses for testing.
     *
     * @param prompt the prompt key
     * @param response the response value
     */
    public void registerResponse(String prompt, String response) {
        this.responses.put(prompt, response);
    }

    /**
     * Clear all registered responses (useful for test isolation).
     */
    public void clear() {
        this.responses.clear();
        initializeMockResponses();
    }
}
