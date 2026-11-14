package io.paragon.cli.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.paragon.cli.model.TestSuite;

import java.io.File;
import java.io.IOException;

/**
 * Loads test suites from YAML files.
 * Handles parsing and validation of test suite configurations.
 */
public class YamlLoader {
    private final ObjectMapper objectMapper;

    public YamlLoader() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    /**
     * Loads a test suite from a YAML file.
     *
     * @param filePath path to the YAML file
     * @return parsed TestSuite object
     * @throws IOException if file cannot be read or parsed
     */
    public TestSuite loadTestSuite(String filePath) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new IOException("Test file not found: " + filePath);
        }
        
        if (!file.canRead()) {
            throw new IOException("Cannot read test file: " + filePath);
        }
        
        try {
            TestSuite testSuite = objectMapper.readValue(file, TestSuite.class);
            
            if (testSuite == null || testSuite.getTests() == null || testSuite.getTests().isEmpty()) {
                throw new IOException("Test suite is empty or invalid");
            }
            
            if (testSuite.getProvider() == null || testSuite.getProvider().trim().isEmpty()) {
                throw new IOException("Provider not specified in test suite");
            }
            
            return testSuite;
        } catch (IOException e) {
            throw new IOException("Failed to parse YAML file: " + e.getMessage(), e);
        }
    }

    /**
     * Validates a test suite for completeness.
     *
     * @param testSuite the test suite to validate
     * @throws IllegalArgumentException if validation fails
     */
    public void validate(TestSuite testSuite) throws IllegalArgumentException {
        if (testSuite == null) {
            throw new IllegalArgumentException("Test suite cannot be null");
        }
        
        if (testSuite.getProvider() == null || testSuite.getProvider().trim().isEmpty()) {
            throw new IllegalArgumentException("Provider must be specified");
        }
        
        if (testSuite.getTests() == null || testSuite.getTests().isEmpty()) {
            throw new IllegalArgumentException("Test suite must contain at least one test");
        }
        
        testSuite.getTests().forEach(testCase -> {
            if (testCase.getName() == null || testCase.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Test case name is required");
            }
            
            if (testCase.getPrompt() == null || testCase.getPrompt().trim().isEmpty()) {
                throw new IllegalArgumentException("Test case prompt is required: " + testCase.getName());
            }
            
            if (testCase.getAssertions() == null || testCase.getAssertions().isEmpty()) {
                throw new IllegalArgumentException("Test case must have at least one assertion: " + testCase.getName());
            }
            
            testCase.getAssertions().forEach(assertion -> {
                if (assertion.getType() == null || assertion.getType().trim().isEmpty()) {
                    throw new IllegalArgumentException("Assertion type is required in: " + testCase.getName());
                }
            });
        });
    }
}
