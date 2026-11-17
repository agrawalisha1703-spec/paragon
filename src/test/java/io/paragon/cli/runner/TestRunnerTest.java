package io.paragon.cli.runner;

import io.paragon.cli.model.Assertion;
import io.paragon.cli.model.TestCase;
import io.paragon.cli.model.TestSuite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TestRunner Tests")
class TestRunnerTest {

    @Test
    @DisplayName("Should create TestRunner with mock provider")
    void testCreateWithMockProvider() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        suite.setTests(java.util.List.of());
        
        TestRunner runner = TestRunner.create(suite);
        assertNotNull(runner);
    }

    @Test
    @DisplayName("Should throw for unsupported provider")
    void testUnsupportedProvider() {
        TestSuite suite = new TestSuite();
        suite.setProvider("unsupported");
        
        assertThrows(Exception.class, () -> {
            TestRunner.create(suite);
        });
    }

    @Test
    @DisplayName("Should execute empty test suite")
    void testExecuteEmptyTestSuite() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        suite.setTests(java.util.List.of());
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertNotNull(result);
        assertEquals(0, result.getTotalCount());
    }

    @Test
    @DisplayName("Should run single test case")
    void testRunSingleTestCase() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        TestCase testCase = new TestCase();
        testCase.setName("Test 1");
        testCase.setPrompt("Say hello to the user");
        
        Assertion assertion = new Assertion();
        assertion.setType("contains");
        assertion.setValue("Hello");
        testCase.setAssertions(java.util.List.of(assertion));
        
        suite.setTests(java.util.List.of(testCase));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertNotNull(result);
        assertEquals(1, result.getTotalCount());
        assertTrue(result.allPassed());
    }

    @Test
    @DisplayName("Should handle multiple test cases")
    void testRunMultipleTestCases() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        TestCase testCase1 = new TestCase();
        testCase1.setName("Test 1");
        testCase1.setPrompt("Say hello to the user");
        Assertion assertion1 = new Assertion();
        assertion1.setType("contains");
        assertion1.setValue("Hello");
        testCase1.setAssertions(java.util.List.of(assertion1));
        
        TestCase testCase2 = new TestCase();
        testCase2.setName("Test 2");
        testCase2.setPrompt("Generate a JSON invoice with fields: id, total, date");
        Assertion assertion2 = new Assertion();
        assertion2.setType("isValidJson");
        testCase2.setAssertions(java.util.List.of(assertion2));
        
        suite.setTests(java.util.List.of(testCase1, testCase2));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertNotNull(result);
        assertEquals(2, result.getTotalCount());
        assertTrue(result.allPassed());
    }

    @Test
    @DisplayName("TestRunResult should report failed tests")
    void testRunResultWithFailures() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        TestCase testCase = new TestCase();
        testCase.setName("Failing Test");
        testCase.setPrompt("Say hello to the user");
        
        Assertion assertion = new Assertion();
        assertion.setType("contains");
        assertion.setValue("NONEXISTENT");
        testCase.setAssertions(java.util.List.of(assertion));
        
        suite.setTests(java.util.List.of(testCase));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertNotNull(result);
        assertEquals(1, result.getTotalCount());
        assertFalse(result.allPassed());
    }

    @Test
    @DisplayName("TestRunResult should calculate exit code correctly")
    void testExitCodeForPassingTests() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        TestCase testCase = new TestCase();
        testCase.setName("Passing Test");
        testCase.setPrompt("Say hello to the user");
        
        Assertion assertion = new Assertion();
        assertion.setType("contains");
        assertion.setValue("Hello");
        testCase.setAssertions(java.util.List.of(assertion));
        
        suite.setTests(java.util.List.of(testCase));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertEquals(0, result.getExitCode());
    }

    @Test
    @DisplayName("TestRunResult should return 1 exit code for failures")
    void testExitCodeForFailingTests() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        TestCase testCase = new TestCase();
        testCase.setName("Failing Test");
        testCase.setPrompt("Say hello to the user");
        
        Assertion assertion = new Assertion();
        assertion.setType("contains");
        assertion.setValue("NONEXISTENT");
        testCase.setAssertions(java.util.List.of(assertion));
        
        suite.setTests(java.util.List.of(testCase));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertEquals(1, result.getExitCode());
    }

    @Test
    @DisplayName("TestRunResult should count passed tests")
    void testPassedTestCount() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setProvider("mock");
        
        // Create 2 passing tests
        TestCase test1 = new TestCase();
        test1.setName("Test 1");
        test1.setPrompt("Say hello to the user");
        Assertion assertion1 = new Assertion();
        assertion1.setType("contains");
        assertion1.setValue("Hello");
        test1.setAssertions(java.util.List.of(assertion1));
        
        TestCase test2 = new TestCase();
        test2.setName("Test 2");
        test2.setPrompt("Generate a JSON invoice with fields: id, total, date");
        Assertion assertion2 = new Assertion();
        assertion2.setType("isValidJson");
        test2.setAssertions(java.util.List.of(assertion2));
        
        suite.setTests(java.util.List.of(test1, test2));
        
        TestRunner runner = TestRunner.create(suite);
        TestRunner.TestRunResult result = runner.runTests(suite);
        
        assertEquals(2, result.getPassedCount());
    }
}
