package io.paragon.cli.integration;

import io.paragon.cli.loader.YamlLoader;
import io.paragon.cli.model.TestSuite;
import io.paragon.cli.report.ConsoleReporter;
import io.paragon.cli.runner.TestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for end-to-end test execution workflows.
 */
public class EndToEndTest {
    
    private final YamlLoader loader = new YamlLoader();
    private final ConsoleReporter reporter = new ConsoleReporter();
    
    @Test
    @DisplayName("Should execute complete test workflow with YAML file")
    public void testCompleteWorkflow() throws Exception {
        // Load test suite
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        assertNotNull(suite, "Test suite should load successfully");
        
        // Validate suite
        assertDoesNotThrow(() -> loader.validate(suite), "Suite validation should pass");
        
        // Create runner
        TestRunner runner = TestRunner.create(suite);
        assertNotNull(runner, "Runner should be created");
        
        // Run tests
        TestRunner.TestRunResult result = runner.runTests(suite);
        assertNotNull(result, "Result should not be null");
        
        // Verify success
        assertTrue(result.allPassed(), "All tests should pass");
        assertEquals(0, result.getExitCode(), "Exit code should be 0 for success");
    }
    
    @Test
    @DisplayName("Should handle multiple tests correctly")
    public void testMultipleTests() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/comprehensive-tests.yaml");
        
        // Run tests
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        // Verify
        assertEquals(4, result.getTotalCount(), "Should have 4 tests");
        assertEquals(4, result.getPassedCount(), "All 4 tests should pass");
        assertTrue(result.allPassed());
    }
    
    @Test
    @DisplayName("Should validate YAML schema correctly")
    public void testYamlValidation() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        
        // Should not throw
        assertDoesNotThrow(() -> loader.validate(suite));
        
        // Should have required fields
        assertNotNull(suite.getProvider());
        assertNotNull(suite.getTests());
        assertFalse(suite.getTests().isEmpty());
    }
    
    @Test
    @DisplayName("Should handle missing test file gracefully")
    public void testMissingTestFile() {
        assertThrows(Exception.class, () -> {
            loader.loadTestSuite("nonexistent.yaml");
        }, "Should throw exception for missing file");
    }
    
    @Test
    @DisplayName("Should execute all assertions per test")
    public void testAssertionExecution() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        // First test has 5 assertions
        int firstTestAssertions = result.getTestCaseResults().get(0).getAssertionResults().size();
        assertEquals(5, firstTestAssertions, "First test should have 5 assertions");
    }
    
    @Test
    @DisplayName("Should maintain provider configuration")
    public void testProviderConfiguration() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        
        assertEquals("mock", suite.getProvider(), "Provider should be 'mock'");
        assertNotNull(suite.getTests());
    }
    
    @Test
    @DisplayName("Should generate valid exit codes")
    public void testExitCodes() throws Exception {
        TestSuite suite = loader.loadTestSuite("sample-tests/invoice-generation.yaml");
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        // Success = 0, Failure = 1
        if (result.allPassed()) {
            assertEquals(0, result.getExitCode());
        } else {
            assertEquals(1, result.getExitCode());
        }
    }
}
