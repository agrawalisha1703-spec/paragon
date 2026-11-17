package io.paragon.cli.assertion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IsValidJsonAssertion Tests")
class IsValidJsonAssertionTest {

    @Test
    @DisplayName("Should pass for valid JSON object")
    void testValidJsonObject() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("{\"name\": \"John\", \"age\": 30}"));
    }

    @Test
    @DisplayName("Should pass for valid JSON array")
    void testValidJsonArray() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("[1, 2, 3, 4, 5]"));
    }

    @Test
    @DisplayName("Should pass for JSON string")
    void testValidJsonString() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("\"hello\""));
    }

    @Test
    @DisplayName("Should pass for JSON number")
    void testValidJsonNumber() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("42"));
    }

    @Test
    @DisplayName("Should pass for JSON boolean")
    void testValidJsonBoolean() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("true"));
        assertTrue(assertion.evaluate("false"));
    }

    @Test
    @DisplayName("Should pass for JSON null")
    void testValidJsonNull() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("null"));
    }

    @Test
    @DisplayName("Should fail for invalid JSON")
    void testInvalidJson() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertFalse(assertion.evaluate("{invalid}"));
    }

    @Test
    @DisplayName("Should fail for plain text")
    void testPlainText() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should fail for missing closing brace")
    void testMissingClosingBrace() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertFalse(assertion.evaluate("{\"name\": \"John\""));
    }

    @Test
    @DisplayName("Should handle nested objects")
    void testNestedObjects() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("{\"person\": {\"name\": \"John\", \"age\": 30}}"));
    }

    @Test
    @DisplayName("Should handle nested arrays")
    void testNestedArrays() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("[[1, 2], [3, 4]]"));
    }

    @Test
    @DisplayName("Should handle mixed nested structures")
    void testMixedNestedStructures() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("{\"items\": [1, {\"id\": 2}, 3]}"));
    }

    @Test
    @DisplayName("Should return correct type")
    void testGetType() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertEquals("isValidJson", assertion.getType());
    }

    @Test
    @DisplayName("Should handle empty object")
    void testEmptyObject() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("{}"));
    }

    @Test
    @DisplayName("Should handle empty array")
    void testEmptyArray() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("[]"));
    }

    @Test
    @DisplayName("Should handle special characters in strings")
    void testSpecialCharacters() {
        IsValidJsonAssertion assertion = new IsValidJsonAssertion();
        assertTrue(assertion.evaluate("{\"text\": \"Hello\\nWorld\\t!\"}"));
    }
}
