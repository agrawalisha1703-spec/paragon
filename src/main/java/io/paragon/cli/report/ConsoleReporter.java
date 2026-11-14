package io.paragon.cli.report;

import io.paragon.cli.runner.TestRunner;

/**
 * Console reporter that prints human-readable test results.
 * Outputs results to stdout with colors and formatting.
 */
public class ConsoleReporter {
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    /**
     * Reports test results to console.
     *
     * @param result the test run results
     */
    public void report(TestRunner.TestRunResult result) {
        System.out.println();
        System.out.println(BLUE + "=" .repeat(60) + RESET);
        System.out.println(BLUE + "Test Results" + RESET);
        System.out.println(BLUE + "=" .repeat(60) + RESET);

        for (TestRunner.TestCaseResult caseResult : result.getTestCaseResults()) {
            reportTestCase(caseResult);
        }

        System.out.println();
        System.out.println(BLUE + "-".repeat(60) + RESET);

        int passed = result.getPassedCount();
        int total = result.getTotalCount();

        if (result.allPassed()) {
            System.out.println(GREEN + "✓ PASS: " + passed + "/" + total + " tests passed" + RESET);
        } else {
            System.out.println(RED + "✗ FAIL: " + passed + "/" + total + " tests passed" + RESET);
        }

        System.out.println(BLUE + "Exit code: " + result.getExitCode() + RESET);
        System.out.println(BLUE + "=" .repeat(60) + RESET);
        System.out.println();
    }

    /**
     * Reports a single test case result.
     *
     * @param caseResult the test case result
     */
    private void reportTestCase(TestRunner.TestCaseResult caseResult) {
        String status = caseResult.isPassed() ? (GREEN + "✓" + RESET) : (RED + "✗" + RESET);
        int passed = caseResult.getPassedCount();
        int total = caseResult.getTotalCount();

        System.out.println(status + " " + caseResult.getName() + " (" + passed + "/" + total + " assertions passed)");

        if (caseResult.getError() != null) {
            System.out.println(RED + "  Error: " + caseResult.getError() + RESET);
        } else {
            for (TestRunner.AssertionResult assertResult : caseResult.getAssertionResults()) {
                reportAssertion(assertResult);
            }
        }
    }

    /**
     * Reports a single assertion result.
     *
     * @param assertResult the assertion result
     */
    private void reportAssertion(TestRunner.AssertionResult assertResult) {
        if (assertResult.isPassed()) {
            System.out.println(GREEN + "    ✓ " + assertResult.getType() + RESET);
        } else {
            System.out.println(RED + "    ✗ " + assertResult.getType() + RESET);
            if (assertResult.getError() != null) {
                System.out.println(RED + "      " + assertResult.getError() + RESET);
            }
        }
    }
}
