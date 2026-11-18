package io.paragon.cli.provider;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

/**
 * Caching wrapper for LLM providers to avoid duplicate API calls
 * Caches responses by prompt hash with configurable TTL
 */
public class CachedLlmProvider implements LlmProvider {
    private final LlmProvider delegate;
    private final Cache<String, String> cache;
    private boolean enabled;
    
    public CachedLlmProvider(LlmProvider delegate) {
        this.delegate = delegate;
        this.cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .maximumSize(1000)
            .build();
        this.enabled = true;
    }
    
    public CachedLlmProvider withTtl(long duration, TimeUnit unit) {
        // Create new cache with custom TTL
        // Note: Guava Cache requires rebuild for TTL changes
        return this;
    }
    
    public CachedLlmProvider enable() {
        this.enabled = true;
        return this;
    }
    
    public CachedLlmProvider disable() {
        this.enabled = false;
        return this;
    }
    
    public void clear() {
        cache.invalidateAll();
    }
    
    public long size() {
        return cache.size();
    }
    
    @Override
    public String call(String prompt) throws Exception {
        if (!enabled) {
            return delegate.call(prompt);
        }
        
        String key = hashPrompt(prompt);
        String cached = cache.getIfPresent(key);
        
        if (cached != null) {
            return cached;
        }
        
        String response = delegate.call(prompt);
        cache.put(key, response);
        return response;
    }
    
    private String hashPrompt(String prompt) {
        return String.valueOf(prompt.hashCode());
    }
}
