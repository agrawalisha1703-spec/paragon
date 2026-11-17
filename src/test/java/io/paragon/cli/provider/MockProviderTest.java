package io.paragon.cli.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MockProvider Tests")
class MockProviderTest {

    private MockProvider provider;

    @BeforeEach
    void setUp() {
        provider = new MockProvider();
    }

    @Test
    @DisplayName("Should return response for 'Say hello to the user'")
    void testHelloResponse() throws Exception {
        String response = provider.call("Say hello to the user");
        assertNotNull(response);
        assertTrue(response.contains("Hello"));
    }

    @Test
    @DisplayName("Should return response for 'Generate a JSON invoice'")
    void testInvoiceJsonResponse() throws Exception {
        String response = provider.call("Generate a JSON invoice with fields: id, total, date");
        assertNotNull(response);
        assertTrue(response.contains("INV-001"));
        assertTrue(response.contains("99.99"));
    }

    @Test
    @DisplayName("Should return response for 'Generate a CSV row'")
    void testCsvResponse() throws Exception {
        String response = provider.call("Generate a CSV row with columns: name, age, city");
        assertNotNull(response);
        assertTrue(response.contains("John Doe"));
        assertTrue(response.contains("30"));
        assertTrue(response.contains("San Francisco"));
    }

    @Test
    @DisplayName("Should return deterministic responses")
    void testDeterministicResponses() throws Exception {
        String response1 = provider.call("Say hello to the user");
        String response2 = provider.call("Say hello to the user");
        assertEquals(response1, response2);
    }

    @Test
    @DisplayName("Should return different responses for different prompts")
    void testDifferentResponsesForDifferentPrompts() throws Exception {
        String response1 = provider.call("Say hello to the user");
        String response2 = provider.call("Generate a CSV row with columns: name, age, city");
        assertNotEquals(response1, response2);
    }

    @Test
    @DisplayName("Should handle unknown prompts with fallback")
    void testUnknownPromptFallback() throws Exception {
        String response = provider.call("Unknown prompt that doesn't exist");
        assertNotNull(response);
        // Should return some fallback response
        assertTrue(response.length() > 0);
    }

    @Test
    @DisplayName("Should return response for email subject prompt")
    void testEmailSubjectResponse() throws Exception {
        String response = provider.call("Write a professional email subject for a meeting reminder");
        assertNotNull(response);
        assertTrue(response.contains("Reminder"));
    }

    @Test
    @DisplayName("Should return response for product description")
    void testProductDescriptionResponse() throws Exception {
        String response = provider.call("Generate a product description for a laptop");
        assertNotNull(response);
        assertTrue(response.length() > 50);
    }

    @Test
    @DisplayName("Should never return null")
    void testNeverReturnsNull() throws Exception {
        String[] prompts = {
            "Say hello to the user",
            "Generate a JSON invoice with fields: id, total, date",
            "Generate a CSV row with columns: name, age, city",
            "Unknown prompt"
        };
        
        for (String prompt : prompts) {
            assertNotNull(provider.call(prompt));
        }
    }

    @Test
    @DisplayName("Should return non-empty responses")
    void testNeverReturnsEmpty() throws Exception {
        String[] prompts = {
            "Say hello to the user",
            "Generate a JSON invoice with fields: id, total, date",
            "Generate a CSV row with columns: name, age, city"
        };
        
        for (String prompt : prompts) {
            assertTrue(provider.call(prompt).length() > 0);
        }
    }

    @Test
    @DisplayName("Should handle null prompt gracefully")
    void testNullPromptHandling() throws Exception {
        // Should either throw exception or return fallback
        try {
            String response = provider.call(null);
            assertNotNull(response);
        } catch (NullPointerException e) {
            // Expected behavior
        }
    }

    @Test
    @DisplayName("Should handle empty prompt")
    void testEmptyPromptHandling() throws Exception {
        String response = provider.call("");
        // Should return something (either fallback or specific empty prompt response)
        assertNotNull(response);
    }

    @Test
    @DisplayName("Should handle whitespace prompt")
    void testWhitespacePromptHandling() throws Exception {
        String response = provider.call("   ");
        assertNotNull(response);
    }
}
