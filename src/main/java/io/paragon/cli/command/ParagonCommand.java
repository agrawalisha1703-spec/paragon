package io.paragon.cli.command;

import io.paragon.cli.loader.YamlLoader;
import io.paragon.cli.model.TestSuite;
import io.paragon.cli.report.ConsoleReporter;
import io.paragon.cli.report.JunitXmlReporter;
import io.paragon.cli.runner.TestRunner;
import picocli.commandline.Command;
import picocli.commandline.Option;
import picocli.commandline.Parameters;

import java.util.concurrent.Callable;

/**
 * Main Paragon CLI command.
 * Provides command-line interface for running prompt regression tests.
 */
@Command(
    name = "paragon",
    description = "Regression testing framework for LLM prompts",
    version = "0.1.0",
    mixinStandardHelpOptions = true
)
public class ParagonCommand implements Callable<Integer> {
    @Parameters(
        index = "0",
        description = "Path to the test YAML file"
    )
    private String testFile;

    @Option(
        names = {"-o", "--output"},
        description = "Output path for JUnit XML report (optional)"
    )
    private String outputPath;

    @Option(
        names = {"-v", "--verbose"},
        description = "Enable verbose output"
    )
    private boolean verbose = false;

    @Override
    public Integer call() throws Exception {
        try {
            if (verbose) {
                System.out.println("[INFO] Loading test suite from: " + testFile);
            }

            // Load test suite
            YamlLoader loader = new YamlLoader();
            TestSuite testSuite = loader.loadTestSuite(testFile);

            if (verbose) {
                System.out.println("[INFO] Validating test suite...");
            }

            // Validate
            loader.validate(testSuite);

            if (verbose) {
                System.out.println("[INFO] Creating test runner with provider: " + testSuite.getProvider());
            }

            // Create runner
            TestRunner runner = TestRunner.create(testSuite);

            if (verbose) {
                System.out.println("[INFO] Running " + testSuite.getTests().size() + " tests...");
            }

            // Run tests
            TestRunner.TestRunResult result = runner.runTests(testSuite);

            // Report to console
            ConsoleReporter consoleReporter = new ConsoleReporter();
            consoleReporter.report(result);

            // Report to XML if specified
            if (outputPath != null && !outputPath.isEmpty()) {
                if (verbose) {
                    System.out.println("[INFO] Writing JUnit XML report to: " + outputPath);
                }
                JunitXmlReporter xmlReporter = new JunitXmlReporter(outputPath);
                xmlReporter.report(result);
            }

            // Return exit code
            return result.getExitCode();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
            return 1;
        }
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int exitCode = new picocli.commandline.CommandLine(new ParagonCommand()).execute(args);
        System.exit(exitCode);
    }
}
