package io.paragon.cli.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OpenAI Provider Tests")
class OpenAiProviderTest {
    
    @Test
    @DisplayName("Should throw exception when API key is missing")
    void testMissingApiKey() {
        OpenAiProvider provider = new OpenAiProvider(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            provider.call("test prompt");
        });
        
        assertTrue(exception.getMessage().contains("API key"));
    }
    
    @Test
    @DisplayName("Should throw exception when API key is empty")
    void testEmptyApiKey() {
        OpenAiProvider provider = new OpenAiProvider("");
        
        Exception exception = assertThrows(Exception.class, () -> {
            provider.call("test prompt");
        });
        
        assertTrue(exception.getMessage().contains("API key"));
    }
    
    @Test
    @DisplayName("Should support model configuration")
    void testModelConfiguration() {
        OpenAiProvider provider = new OpenAiProvider("test-key")
            .withModel("gpt-4");
        
        assertNotNull(provider);
    }
}
