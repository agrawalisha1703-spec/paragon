package io.paragon.cli.assertion;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Assertion that checks if output is valid JSON.
 * Output must be parseable as valid JSON.
 */
public class IsValidJsonAssertion implements AssertionEngine {
    private final ObjectMapper objectMapper;

    public IsValidJsonAssertion() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean evaluate(String output) {
        if (output == null || output.trim().isEmpty()) {
            return false;
        }

        try {
            objectMapper.readTree(output);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Output must be valid JSON";
    }

    @Override
    public String getType() {
        return "isValidJson";
    }

    @Override
    public String toString() {
        return "IsValidJsonAssertion{}";
    }
}
