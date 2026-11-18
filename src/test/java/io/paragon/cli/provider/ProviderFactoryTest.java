package io.paragon.cli.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Provider Factory Tests")
class ProviderFactoryTest {
    
    @Test
    @DisplayName("Should create MockProvider by default")
    void testCreateMockProvider() {
        LlmProvider provider = ProviderFactory.create("mock", null);
        
        assertNotNull(provider);
        assertInstanceOf(MockProvider.class, provider);
    }
    
    @Test
    @DisplayName("Should create OpenAiProvider when specified")
    void testCreateOpenAiProvider() {
        LlmProvider provider = ProviderFactory.create("openai", "test-key");
        
        assertNotNull(provider);
        assertInstanceOf(OpenAiProvider.class, provider);
    }
    
    @Test
    @DisplayName("Should create AnthropicProvider when specified")
    void testCreateAnthropicProvider() {
        LlmProvider provider = ProviderFactory.create("anthropic", "test-key");
        
        assertNotNull(provider);
        assertInstanceOf(AnthropicProvider.class, provider);
    }
    
    @Test
    @DisplayName("Should wrap provider with cache when enabled")
    void testCreateWithCache() {
        LlmProvider provider = ProviderFactory.create("mock", null, true);
        
        assertNotNull(provider);
        assertInstanceOf(CachedLlmProvider.class, provider);
    }
    
    @Test
    @DisplayName("Should handle case-insensitive provider names")
    void testCaseInsensitiveProvider() {
        LlmProvider provider1 = ProviderFactory.create("MOCK", null);
        LlmProvider provider2 = ProviderFactory.create("Mock", null);
        
        assertInstanceOf(MockProvider.class, provider1);
        assertInstanceOf(MockProvider.class, provider2);
    }
    
    @Test
    @DisplayName("Should create provider from environment variables")
    void testCreateWithConfig() {
        // This will use env vars if available
        LlmProvider provider = ProviderFactory.createWithConfig("mock", false);
        
        assertNotNull(provider);
    }
}
