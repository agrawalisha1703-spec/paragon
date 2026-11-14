package io.paragon.cli.runner;

import io.paragon.cli.assertion.AssertionEngine;
import io.paragon.cli.assertion.AssertionFactory;
import io.paragon.cli.model.Assertion;
import io.paragon.cli.model.TestCase;
import io.paragon.cli.model.TestSuite;
import io.paragon.cli.provider.LlmProvider;
import io.paragon.cli.provider.MockProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrates test execution: load test suite → call provider → run assertions → collect results.
 * Handles provider selection and test execution flow.
 */
public class TestRunner {
    private final LlmProvider provider;

    public TestRunner(LlmProvider provider) {
        this.provider = provider;
    }

    /**
     * Creates a TestRunner with the appropriate provider based on test suite configuration.
     *
     * @param testSuite the test suite configuration
     * @return a TestRunner with the selected provider
     * @throws IllegalArgumentException if provider is not supported
     */
    public static TestRunner create(TestSuite testSuite) throws Exception {
        String providerName = testSuite.getProvider().toLowerCase().trim();

        LlmProvider provider;
        if ("mock".equals(providerName)) {
            provider = new MockProvider();
        } else {
            throw new IllegalArgumentException("Unsupported provider: " + providerName + 
                    ". Supported: mock");
        }

        return new TestRunner(provider);
    }

    /**
     * Runs all tests in a test suite.
     *
     * @param testSuite the test suite to run
     * @return test run results
     * @throws Exception if execution fails
     */
    public TestRunResult runTests(TestSuite testSuite) throws Exception {
        TestRunResult result = new TestRunResult();

        for (TestCase testCase : testSuite.getTests()) {
            TestCaseResult caseResult = runTestCase(testCase);
            result.addTestCaseResult(caseResult);
        }

        return result;
    }

    /**
     * Runs a single test case.
     *
     * @param testCase the test case to run
     * @return result of the test case
     */
    private TestCaseResult runTestCase(TestCase testCase) {
        TestCaseResult result = new TestCaseResult(testCase.getName());

        try {
            // Call the provider with the prompt
            String output = provider.call(testCase.getPrompt());
            result.setOutput(output);

            // Run all assertions
            for (Assertion assertion : testCase.getAssertions()) {
                AssertionEngine engine = AssertionFactory.createAssertion(assertion);
                AssertionResult assertionResult = runAssertion(engine, output);
                result.addAssertionResult(assertionResult);
            }
        } catch (Exception e) {
            result.setError("Test execution failed: " + e.getMessage());
        }

        return result;
    }

    /**
     * Runs a single assertion.
     *
     * @param engine the assertion engine
     * @param output the LLM output to check
     * @return assertion result
     */
    private AssertionResult runAssertion(AssertionEngine engine, String output) {
        AssertionResult result = new AssertionResult(engine.getType(), engine.getDescription());

        try {
            boolean passed = engine.evaluate(output);
            result.setPassed(passed);
        } catch (Exception e) {
            result.setError("Assertion evaluation failed: " + e.getMessage());
        }

        return result;
    }

    /**
     * Represents a single assertion result.
     */
    public static class AssertionResult {
        private final String type;
        private final String description;
        private boolean passed;
        private String error;

        public AssertionResult(String type, String description) {
            this.type = type;
            this.description = description;
            this.passed = false;
        }

        public String getType() { return type; }
        public String getDescription() { return description; }
        public boolean isPassed() { return passed; }
        public void setPassed(boolean passed) { this.passed = passed; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        @Override
        public String toString() {
            if (passed) {
                return "✓ " + type + ": " + description;
            } else if (error != null) {
                return "✗ " + type + ": " + error;
            } else {
                return "✗ " + type + ": Assertion failed";
            }
        }
    }

    /**
     * Represents results of a single test case.
     */
    public static class TestCaseResult {
        private final String name;
        private String output;
        private String error;
        private final List<AssertionResult> assertionResults;

        public TestCaseResult(String name) {
            this.name = name;
            this.assertionResults = new ArrayList<>();
        }

        public String getName() { return name; }
        public String getOutput() { return output; }
        public void setOutput(String output) { this.output = output; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public List<AssertionResult> getAssertionResults() { return assertionResults; }
        public void addAssertionResult(AssertionResult result) { this.assertionResults.add(result); }

        public boolean isPassed() {
            if (error != null) return false;
            return assertionResults.stream().allMatch(AssertionResult::isPassed);
        }

        public int getPassedCount() {
            return (int) assertionResults.stream().filter(AssertionResult::isPassed).count();
        }

        public int getTotalCount() {
            return assertionResults.size();
        }
    }

    /**
     * Represents results of an entire test run.
     */
    public static class TestRunResult {
        private final List<TestCaseResult> testCaseResults;

        public TestRunResult() {
            this.testCaseResults = new ArrayList<>();
        }

        public List<TestCaseResult> getTestCaseResults() { return testCaseResults; }
        public void addTestCaseResult(TestCaseResult result) { this.testCaseResults.add(result); }

        public boolean allPassed() {
            return testCaseResults.stream().allMatch(TestCaseResult::isPassed);
        }

        public int getPassedCount() {
            return (int) testCaseResults.stream().filter(TestCaseResult::isPassed).count();
        }

        public int getTotalCount() {
            return testCaseResults.size();
        }

        public int getExitCode() {
            return allPassed() ? 0 : 1;
        }
    }
}
