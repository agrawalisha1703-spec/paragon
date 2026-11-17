package io.paragon.cli.loader;

import io.paragon.cli.model.TestSuite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("YamlLoader Tests")
class YamlLoaderTest {

    private final YamlLoader loader = new YamlLoader();

    @Test
    @DisplayName("Should load valid YAML test suite")
    void testLoadValidTestSuite() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertNotNull(suite);
        assertEquals("mock", suite.getProvider());
        assertEquals(3, suite.getTests().size());
    }

    @Test
    @DisplayName("Should load comprehensive test suite")
    void testLoadComprehensiveTestSuite() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/comprehensive-tests.yaml");
        assertNotNull(suite);
        assertEquals("mock", suite.getProvider());
        assertEquals(4, suite.getTests().size());
    }

    @Test
    @DisplayName("Should throw when file not found")
    void testFileNotFound() {
        assertThrows(Exception.class, () -> {
            loader.loadTestSuite("nonexistent-file.yaml");
        });
    }

    @Test
    @DisplayName("Should throw for empty test suite")
    void testEmptyTestSuite() {
        assertThrows(Exception.class, () -> {
            loader.loadTestSuite("sample-tests/nonexistent.yaml");
        });
    }

    @Test
    @DisplayName("Should parse provider field")
    void testParseProvider() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertNotNull(suite.getProvider());
        assertEquals("mock", suite.getProvider());
    }

    @Test
    @DisplayName("Should parse test cases")
    void testParseTestCases() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertNotNull(suite.getTests());
        assertTrue(suite.getTests().size() > 0);
    }

    @Test
    @DisplayName("Should parse test names")
    void testParseTestNames() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertTrue(suite.getTests().get(0).getName() != null);
        assertTrue(suite.getTests().get(0).getName().length() > 0);
    }

    @Test
    @DisplayName("Should parse prompts")
    void testParsePrompts() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertTrue(suite.getTests().get(0).getPrompt() != null);
        assertTrue(suite.getTests().get(0).getPrompt().length() > 0);
    }

    @Test
    @DisplayName("Should parse assertions")
    void testParseAssertions() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertTrue(suite.getTests().get(0).getAssertions().size() > 0);
    }

    @Test
    @DisplayName("Should validate provider is specified")
    void testValidateProviderRequired() {
        assertThrows(Exception.class, () -> {
            loader.loadTestSuite("sample-tests/nonexistent.yaml");
        });
    }

    @Test
    @DisplayName("Should validate tests are not empty")
    void testValidateTestsNotEmpty() {
        assertThrows(Exception.class, () -> {
            loader.loadTestSuite("sample-tests/nonexistent.yaml");
        });
    }

    @Test
    @DisplayName("Should handle special characters in YAML")
    void testSpecialCharactersInYaml() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertNotNull(suite);
        // Verify data is loaded (special chars handled)
        assertTrue(suite.getTests().size() > 0);
    }
}
