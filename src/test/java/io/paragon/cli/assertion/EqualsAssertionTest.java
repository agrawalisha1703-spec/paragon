package io.paragon.cli.assertion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EqualsAssertion Tests")
class EqualsAssertionTest {

    @Test
    @DisplayName("Should pass when strings are exactly equal")
    void testExactMatch() {
        EqualsAssertion assertion = new EqualsAssertion("Hello World");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should fail when strings differ in case")
    void testCaseSensitive() {
        EqualsAssertion assertion = new EqualsAssertion("Hello");
        assertFalse(assertion.evaluate("hello"));
    }

    @Test
    @DisplayName("Should fail when strings differ")
    void testNotEqual() {
        EqualsAssertion assertion = new EqualsAssertion("Expected");
        assertFalse(assertion.evaluate("Actual"));
    }

    @Test
    @DisplayName("Should handle null values")
    void testNullHandling() {
        EqualsAssertion assertion = new EqualsAssertion(null);
        assertTrue(assertion.evaluate(null));
        assertFalse(assertion.evaluate("Not null"));
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        EqualsAssertion assertion = new EqualsAssertion("");
        assertTrue(assertion.evaluate(""));
        assertFalse(assertion.evaluate(" "));
    }

    @Test
    @DisplayName("Should return correct type")
    void testGetType() {
        EqualsAssertion assertion = new EqualsAssertion("test");
        assertEquals("equals", assertion.getType());
    }

    @Test
    @DisplayName("Should return correct description")
    void testGetDescription() {
        EqualsAssertion assertion = new EqualsAssertion("test value");
        String description = assertion.getDescription();
        assertTrue(description.length() > 0);
    }

    @Test
    @DisplayName("Should handle special characters")
    void testSpecialCharacters() {
        EqualsAssertion assertion = new EqualsAssertion("Hello @#$% World!");
        assertTrue(assertion.evaluate("Hello @#$% World!"));
        assertFalse(assertion.evaluate("Hello World!"));
    }

    @Test
    @DisplayName("Should handle multiline strings")
    void testMultilineStrings() {
        String multiline = "Line 1\nLine 2\nLine 3";
        EqualsAssertion assertion = new EqualsAssertion(multiline);
        assertTrue(assertion.evaluate(multiline));
        assertFalse(assertion.evaluate("Line 1\nLine 2"));
    }

    @Test
    @DisplayName("Should be case-sensitive with numbers")
    void testWithNumbers() {
        EqualsAssertion assertion = new EqualsAssertion("Invoice 123");
        assertTrue(assertion.evaluate("Invoice 123"));
        assertFalse(assertion.evaluate("Invoice 124"));
    }
}
