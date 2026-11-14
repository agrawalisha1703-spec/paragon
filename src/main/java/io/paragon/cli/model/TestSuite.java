package io.paragon.cli.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a test suite containing multiple test cases.
 * A test suite defines which LLM provider to use and the list of tests.
 */
public class TestSuite {
    private String provider;  // mock, openai, anthropic, etc.
    private List<TestCase> tests;

    public TestSuite() {
        this.tests = new ArrayList<>();
    }

    public TestSuite(String provider) {
        this.provider = provider;
        this.tests = new ArrayList<>();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<TestCase> getTests() {
        return tests;
    }

    public void setTests(List<TestCase> tests) {
        this.tests = tests;
    }

    public void addTest(TestCase testCase) {
        this.tests.add(testCase);
    }

    @Override
    public String toString() {
        return "TestSuite{" +
                "provider='" + provider + '\'' +
                ", tests=" + tests +
                '}';
    }
}
