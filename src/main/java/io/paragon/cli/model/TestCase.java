package io.paragon.cli.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single test case within a test suite.
 * A test case consists of a name, a prompt to evaluate, and assertions to verify the output.
 */
public class TestCase {
    private String name;
    private String prompt;
    private List<Assertion> assertions;

    public TestCase() {
        this.assertions = new ArrayList<>();
    }

    public TestCase(String name, String prompt) {
        this.name = name;
        this.prompt = prompt;
        this.assertions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<Assertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<Assertion> assertions) {
        this.assertions = assertions;
    }

    public void addAssertion(Assertion assertion) {
        this.assertions.add(assertion);
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "name='" + name + '\'' +
                ", prompt='" + prompt + '\'' +
                ", assertions=" + assertions +
                '}';
    }
}
