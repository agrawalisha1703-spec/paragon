package io.paragon.cli.assertion;

/**
 * Assertion that checks for exact string match.
 * Output must exactly equal the expected value.
 */
public class EqualsAssertion implements AssertionEngine {
    private final String expected;

    public EqualsAssertion(String expected) {
        this.expected = expected;
    }

    @Override
    public boolean evaluate(String output) {
        if (output == null) {
            return expected == null;
        }
        return output.equals(expected);
    }

    @Override
    public String getDescription() {
        return "Output must exactly equal: " + expected;
    }

    @Override
    public String getType() {
        return "equals";
    }

    @Override
    public String toString() {
        return "EqualsAssertion{" + "expected='" + expected + '\'' + '}';
    }
}
