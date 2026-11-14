package io.paragon.cli.assertion;

import java.util.regex.Pattern;

/**
 * Assertion that checks if output matches a regex pattern.
 * Output must match the specified regular expression.
 */
public class RegexAssertion implements AssertionEngine {
    private final String pattern;
    private final Pattern compiledPattern;

    public RegexAssertion(String pattern) {
        this.pattern = pattern;
        try {
            this.compiledPattern = Pattern.compile(pattern);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + pattern, e);
        }
    }

    @Override
    public boolean evaluate(String output) {
        if (output == null) {
            return false;
        }
        return compiledPattern.matcher(output).find();
    }

    @Override
    public String getDescription() {
        return "Output must match regex: " + pattern;
    }

    @Override
    public String getType() {
        return "regex";
    }

    @Override
    public String toString() {
        return "RegexAssertion{" + "pattern='" + pattern + '\'' + '}';
    }
}
