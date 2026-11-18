package io.paragon.cli.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cached LLM Provider Tests")
class CachedLlmProviderTest {
    private CachedLlmProvider cachedProvider;
    private MockProvider mockProvider;
    
    @BeforeEach
    void setUp() {
        mockProvider = new MockProvider();
        cachedProvider = new CachedLlmProvider(mockProvider);
    }
    
    @Test
    @DisplayName("Should cache response on first call")
    void testCachingOnFirstCall() throws Exception {
        String response = cachedProvider.call("test prompt");
        
        assertTrue(response.contains("test prompt"));
        assertEquals(1, cachedProvider.size());
    }
    
    @Test
    @DisplayName("Should reuse cached response on second call")
    void testCachingOnSecondCall() throws Exception {
        cachedProvider.call("same prompt");
        cachedProvider.call("same prompt");
        
        // Should have only one entry for same prompt
        assertEquals(1, cachedProvider.size());
    }
    
    @Test
    @DisplayName("Should disable caching when disabled")
    void testDisableCaching() throws Exception {
        cachedProvider.disable();
        
        cachedProvider.call("prompt");
        
        assertEquals(0, cachedProvider.size());
    }
    
    @Test
    @DisplayName("Should clear cache on explicit call")
    void testClearCache() throws Exception {
        cachedProvider.call("prompt1");
        cachedProvider.call("prompt2");
        
        assertEquals(2, cachedProvider.size());
        
        cachedProvider.clear();
        
        assertEquals(0, cachedProvider.size());
    }
    
    @Test
    @DisplayName("Should handle different prompts separately")
    void testDifferentPrompts() throws Exception {
        cachedProvider.call("prompt1");
        cachedProvider.call("prompt2");
        cachedProvider.call("prompt3");
        
        assertEquals(3, cachedProvider.size());
    }
}
