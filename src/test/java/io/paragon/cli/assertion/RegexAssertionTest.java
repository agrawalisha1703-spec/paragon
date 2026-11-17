package io.paragon.cli.assertion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RegexAssertion Tests")
class RegexAssertionTest {

    @Test
    @DisplayName("Should match simple pattern")
    void testSimplePattern() {
        RegexAssertion assertion = new RegexAssertion("Hello.*");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should fail when pattern doesn't match")
    void testPatternNotMatch() {
        RegexAssertion assertion = new RegexAssertion("^Goodbye");
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should match digits")
    void testDigitsPattern() {
        RegexAssertion assertion = new RegexAssertion("\\d+");
        assertTrue(assertion.evaluate("Invoice 123"));
    }

    @Test
    @DisplayName("Should match start anchor")
    void testStartAnchor() {
        RegexAssertion assertion = new RegexAssertion("^Hello");
        assertTrue(assertion.evaluate("Hello World"));
        assertFalse(assertion.evaluate("Say Hello"));
    }

    @Test
    @DisplayName("Should match end anchor")
    void testEndAnchor() {
        RegexAssertion assertion = new RegexAssertion("World$");
        assertTrue(assertion.evaluate("Hello World"));
        assertFalse(assertion.evaluate("Hello World Today"));
    }

    @Test
    @DisplayName("Should match character classes")
    void testCharacterClasses() {
        RegexAssertion assertion = new RegexAssertion("[A-Z][a-z]+");
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should match optional patterns")
    void testOptionalPattern() {
        RegexAssertion assertion = new RegexAssertion("Hello( World)?");
        assertTrue(assertion.evaluate("Hello"));
        assertTrue(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should match quantifiers")
    void testQuantifiers() {
        RegexAssertion assertion = new RegexAssertion("\\d{3}");
        assertTrue(assertion.evaluate("Invoice 123"));
        assertFalse(assertion.evaluate("Invoice 12"));
    }

    @Test
    @DisplayName("Should handle escaped characters")
    void testEscapedCharacters() {
        RegexAssertion assertion = new RegexAssertion("\\$\\d+\\.\\d{2}");
        assertTrue(assertion.evaluate("Price: $99.99"));
    }

    @Test
    @DisplayName("Should be case-sensitive by default")
    void testCaseSensitive() {
        RegexAssertion assertion = new RegexAssertion("hello");
        assertFalse(assertion.evaluate("Hello World"));
    }

    @Test
    @DisplayName("Should return correct type")
    void testGetType() {
        RegexAssertion assertion = new RegexAssertion("test");
        assertEquals("regex", assertion.getType());
    }

    @Test
    @DisplayName("Should match email pattern")
    void testEmailPattern() {
        RegexAssertion assertion = new RegexAssertion("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        assertTrue(assertion.evaluate("user@example.com"));
        assertFalse(assertion.evaluate("invalid-email"));
    }

    @Test
    @DisplayName("Should match multiline with dot-all")
    void testMultiline() {
        RegexAssertion assertion = new RegexAssertion("Line.*");
        assertTrue(assertion.evaluate("Line 1\nLine 2"));
    }

    @Test
    @DisplayName("Should handle word boundaries")
    void testWordBoundaries() {
        RegexAssertion assertion = new RegexAssertion("\\bWorld\\b");
        assertTrue(assertion.evaluate("Hello World"));
        assertFalse(assertion.evaluate("HelloWorld"));
    }
}
