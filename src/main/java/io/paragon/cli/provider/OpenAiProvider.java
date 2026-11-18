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
 * OpenAI API provider implementation
 * Supports GPT-3.5-turbo and GPT-4 models
 */
public class OpenAiProvider implements LlmProvider {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String model = "gpt-3.5-turbo";
    
    public OpenAiProvider(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public OpenAiProvider withModel(String model) {
        this.model = model;
        return this;
    }
    
    @Override
    public String call(String prompt) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("OpenAI API key not provided");
        }
        
        // Build request
        String jsonBody = String.format(
            "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":%s}],\"temperature\":0.7}",
            model,
            objectMapper.writeValueAsString(prompt)
        );
        
        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + apiKey)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
            .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                throw new RuntimeException("OpenAI API error (" + response.code() + "): " + errorBody);
            }
            
            String body = response.body().string();
            JsonNode root = objectMapper.readTree(body);
            return root.get("choices").get(0).get("message").get("content").asText();
        }
    }
}
