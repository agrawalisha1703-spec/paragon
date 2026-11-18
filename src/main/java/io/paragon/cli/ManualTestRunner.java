package io.paragon.cli;

import io.paragon.cli.loader.YamlLoader;
import io.paragon.cli.model.TestSuite;
import io.paragon.cli.report.ConsoleReporter;
import io.paragon.cli.runner.TestRunner;
import io.paragon.cli.provider.ProviderFactory;

/**
 * Manual test runner to verify the working of the Paragon CLI infrastructure.
 * Supports multiple providers (mock, openai, anthropic) and response caching.
 */
public class ManualTestRunner {
    public static void main(String[] args) {
        try {
            // Parse command-line arguments
            String testFilePath = "sample-tests/invoice-generation.yaml";
            String apiKey = null;
            boolean enableCache = false;
            String providerOverride = null;
            
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-f") || args[i].equals("--file")) {
                    testFilePath = args[++i];
                } else if (args[i].equals("-k") || args[i].equals("--api-key")) {
                    apiKey = args[++i];
                } else if (args[i].equals("-c") || args[i].equals("--cache")) {
                    enableCache = true;
                } else if (args[i].equals("-p") || args[i].equals("--provider")) {
                    providerOverride = args[++i];
                } else if (!args[i].startsWith("-")) {
                    testFilePath = args[i];
                }
            }
            
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                      Paragon - Test Runner                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
            
            System.out.println("📂 Loading test suite: " + testFilePath);
            
            // Step 1: Load YAML
            YamlLoader loader = new YamlLoader();
            TestSuite testSuite = loader.loadTestSuite(testFilePath);
            
            // Use override provider if provided, otherwise use suite's provider
            String providerName = providerOverride != null ? providerOverride : testSuite.getProvider();
            System.out.println("✅ Loaded test suite: " + providerName + " provider");
            System.out.println("   Total tests: " + testSuite.getTests().size());
            if (enableCache) {
                System.out.println("   Caching: enabled");
            }
            System.out.println();
            
            // Step 2: Create runner with provider and run tests
            System.out.println("🏃 Running tests...\n");
            var llmProvider = ProviderFactory.create(providerName, apiKey, enableCache);
            TestRunner runner = new TestRunner(llmProvider);
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
