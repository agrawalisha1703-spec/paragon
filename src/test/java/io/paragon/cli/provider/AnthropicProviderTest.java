package io.paragon.cli.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Anthropic Provider Tests")
class AnthropicProviderTest {
    
    @Test
    @DisplayName("Should throw exception when API key is missing")
    void testMissingApiKey() {
        AnthropicProvider provider = new AnthropicProvider(null);
        
        Exception exception = assertThrows(Exception.class, () -> {
            provider.call("test prompt");
        });
        
        assertTrue(exception.getMessage().contains("API key"));
    }
    
    @Test
    @DisplayName("Should throw exception when API key is empty")
    void testEmptyApiKey() {
        AnthropicProvider provider = new AnthropicProvider("");
        
        Exception exception = assertThrows(Exception.class, () -> {
            provider.call("test prompt");
        });
        
        assertTrue(exception.getMessage().contains("API key"));
    }
    
    @Test
    @DisplayName("Should support model configuration")
    void testModelConfiguration() {
        AnthropicProvider provider = new AnthropicProvider("test-key")
            .withModel("claude-3-opus-20240229");
        
        assertNotNull(provider);
    }
}
