package io.paragon.cli;

import io.paragon.cli.loader.YamlLoader;
import io.paragon.cli.model.TestSuite;
import io.paragon.cli.report.ConsoleReporter;
import io.paragon.cli.runner.TestRunner;

/**
 * Manual test runner to verify the working of the Paragon CLI infrastructure.
 * This demonstrates end-to-end testing: YAML loading → provider calls → assertions → reporting.
 */
public class ManualTestRunner {
    public static void main(String[] args) {
        try {
            String testFilePath = args.length > 0 ? args[0] : "sample-tests/invoice-generation.yaml";
            
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║           Paragon Phase 1 - Manual Test Execution              ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
            
            System.out.println("📂 Loading test suite: " + testFilePath);
            
            // Step 1: Load YAML
            YamlLoader loader = new YamlLoader();
            TestSuite testSuite = loader.loadTestSuite(testFilePath);
            System.out.println("✅ Loaded test suite: " + testSuite.getProvider() + " provider");
            System.out.println("   Total tests: " + testSuite.getTests().size() + "\n");
            
            // Step 2: Create runner and run tests
            System.out.println("🏃 Running tests...\n");
            TestRunner runner = TestRunner.create(testSuite);
            TestRunner.TestRunResult result = runner.runTests(testSuite);
            
            // Step 3: Report results
            System.out.println("\n📊 Test Results:\n");
            ConsoleReporter reporter = new ConsoleReporter();
            reporter.report(result);
            
            // Step 4: Summary
            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         SUMMARY                                ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝");
            System.out.println("Total Tests: " + result.getTotalCount());
            System.out.println("Passed: " + result.getPassedCount());
            System.out.println("Failed: " + (result.getTotalCount() - result.getPassedCount()));
            System.out.println("Overall: " + (result.allPassed() ? "✅ PASSED" : "❌ FAILED"));
            System.out.println("\nExit Code: " + result.getExitCode());
            
            System.exit(result.getExitCode());
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
