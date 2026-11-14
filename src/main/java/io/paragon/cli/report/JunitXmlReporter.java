package io.paragon.cli.report;

import io.paragon.cli.runner.TestRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

/**
 * JUnit-XML reporter for CI/CD integration.
 * Generates test reports in JUnit XML format compatible with Jenkins, GitHub Actions, etc.
 */
public class JunitXmlReporter {
    private final String outputPath;

    public JunitXmlReporter(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Generates and writes JUnit XML report.
     *
     * @param result the test run results
     * @throws IOException if writing fails
     */
    public void report(TestRunner.TestRunResult result) throws IOException {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<testsuites>\n");
        xml.append("  <testsuite name=\"Paragon\" tests=\"")
                .append(result.getTotalCount()).append("\" ")
                .append("failures=\"").append(result.getTotalCount() - result.getPassedCount()).append("\" ")
                .append("timestamp=\"").append(Instant.now()).append("\">\n");

        for (TestRunner.TestCaseResult caseResult : result.getTestCaseResults()) {
            appendTestCase(xml, caseResult);
        }

        xml.append("  </testsuite>\n");
        xml.append("</testsuites>\n");

        writeToFile(xml.toString());
    }

    /**
     * Appends a test case to the XML.
     *
     * @param xml the XML builder
     * @param caseResult the test case result
     */
    private void appendTestCase(StringBuilder xml, TestRunner.TestCaseResult caseResult) {
        xml.append("    <testcase name=\"").append(escapeXml(caseResult.getName())).append("\">\n");

        if (caseResult.getError() != null) {
            xml.append("      <error message=\"").append(escapeXml(caseResult.getError())).append("\" />\n");
        } else if (!caseResult.isPassed()) {
            for (TestRunner.AssertionResult assertResult : caseResult.getAssertionResults()) {
                if (!assertResult.isPassed()) {
                    String message = assertResult.getError() != null ? 
                            assertResult.getError() : "Assertion failed: " + assertResult.getType();
                    xml.append("      <failure message=\"").append(escapeXml(message)).append("\" />\n");
                }
            }
        }

        xml.append("    </testcase>\n");
    }

    /**
     * Writes XML to file.
     *
     * @param xml the XML content
     * @throws IOException if writing fails
     */
    private void writeToFile(String xml) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(xml);
        }
    }

    /**
     * Escapes XML special characters.
     *
     * @param text the text to escape
     * @return escaped text safe for XML
     */
    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
