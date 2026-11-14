package io.paragon.cli.assertion;

/**
 * Assertion that checks if output contains a substring.
 * Output must contain the specified value (case-sensitive).
 */
public class ContainsAssertion implements AssertionEngine {
    private final String value;

    public ContainsAssertion(String value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(String output) {
        if (output == null || value == null) {
            return false;
        }
        return output.contains(value);
    }

    @Override
    public String getDescription() {
        return "Output must contain: " + value;
    }

    @Override
    public String getType() {
        return "contains";
    }

    @Override
    public String toString() {
        return "ContainsAssertion{" + "value='" + value + '\'' + '}';
    }
}
