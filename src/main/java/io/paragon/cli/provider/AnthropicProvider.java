package io.paragon.cli.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import java.util.concurrent.TimeUnit;

/**
 * Anthropic Claude API provider implementation
 * Supports Claude 3 models (Opus, Sonnet, Haiku)
 */
public class AnthropicProvider implements LlmProvider {
    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String API_VERSION = "2024-06-15";
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String model = "claude-3-sonnet-20240229";
    
    public AnthropicProvider(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public AnthropicProvider withModel(String model) {
        this.model = model;
        return this;
    }
    
    @Override
    public String call(String prompt) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Anthropic API key not provided");
        }
        
        // Build request
        String jsonBody = String.format(
            "{\"model\":\"%s\",\"max_tokens\":1024,\"messages\":[{\"role\":\"user\",\"content\":%s}]}",
            model,
            objectMapper.writeValueAsString(prompt)
        );
        
        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("x-api-key", apiKey)
            .addHeader("anthropic-version", API_VERSION)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
            .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                throw new RuntimeException("Anthropic API error (" + response.code() + "): " + errorBody);
            }
            
            String body = response.body().string();
            JsonNode root = objectMapper.readTree(body);
            return root.get("content").get(0).get("text").asText();
        }
    }
}
