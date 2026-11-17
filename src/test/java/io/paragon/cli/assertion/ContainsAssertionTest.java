package io.paragon.cli.assertion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ContainsAssertion Tests")
class ContainsAssertionTest {

    @Test
    @DisplayName("Should pass when substring exists")
    void testSubstringFound() {
        ContainsAssertion assertion = new ContainsAssertion("World");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should fail when substring doesn't exist")
    void testSubstringNotFound() {
        ContainsAssertion assertion = new ContainsAssertion("Universe");
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should be case-sensitive")
    void testCaseSensitive() {
        ContainsAssertion assertion = new ContainsAssertion("world");
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should find substring at start")
    void testSubstringAtStart() {
        ContainsAssertion assertion = new ContainsAssertion("Hello");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should find substring at end")
    void testSubstringAtEnd() {
        ContainsAssertion assertion = new ContainsAssertion("World");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should find substring in middle")
    void testSubstringInMiddle() {
        ContainsAssertion assertion = new ContainsAssertion("llo W");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should find exact match")
    void testExactMatchAsSubstring() {
        ContainsAssertion assertion = new ContainsAssertion("Hello World");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should handle empty substring")
    void testEmptySubstring() {
        ContainsAssertion assertion = new ContainsAssertion("");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should handle special characters")
    void testSpecialCharacters() {
        ContainsAssertion assertion = new ContainsAssertion("@#$");
        assertTrue(assertion.evaluate("Hello @#$ World"));
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should return correct type")
    void testGetType() {
        ContainsAssertion assertion = new ContainsAssertion("test");
        assertEquals("contains", assertion.getType());
    }

    @Test
    @DisplayName("Should handle numbers")
    void testWithNumbers() {
        ContainsAssertion assertion = new ContainsAssertion("123");
        assertTrue(assertion.evaluate("Invoice 123"));
        assertFalse(assertion.evaluate("Invoice 124"));
    }

    @Test
    @DisplayName("Should handle multiline strings")
    void testMultilineStrings() {
        ContainsAssertion assertion = new ContainsAssertion("Line 2");
        assertTrue(assertion.evaluate("Line 1\nLine 2\nLine 3"));
    }
}
