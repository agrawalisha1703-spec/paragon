package io.paragon.cli.provider;

/**
 * Factory for creating LLM provider instances
 * Supports: mock, openai, anthropic
 */
public class ProviderFactory {
    
    public static LlmProvider create(String providerType, String apiKey) {
        return create(providerType, apiKey, false);
    }
    
    public static LlmProvider create(String providerType, String apiKey, boolean enableCache) {
        LlmProvider provider;
        
        switch (providerType.toLowerCase()) {
            case "openai":
                provider = new OpenAiProvider(apiKey);
                break;
            case "anthropic":
                provider = new AnthropicProvider(apiKey);
                break;
            case "mock":
            default:
                provider = new MockProvider();
                break;
        }
        
        if (enableCache) {
            return new CachedLlmProvider(provider).enable();
        }
        
        return provider;
    }
    
    public static LlmProvider createWithConfig(String providerType, boolean enableCache) {
        // Load API key from environment or config file
        String apiKey = null;
        
        switch (providerType.toLowerCase()) {
            case "openai":
                apiKey = System.getenv("OPENAI_API_KEY");
                break;
            case "anthropic":
                apiKey = System.getenv("ANTHROPIC_API_KEY");
                break;
        }
        
        return create(providerType, apiKey, enableCache);
    }
}
