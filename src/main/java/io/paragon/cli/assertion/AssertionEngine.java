package io.paragon.cli.assertion;

/**
 * Base interface for all assertion types.
 * Each assertion validates a specific condition on the LLM output.
 */
public interface AssertionEngine {
    /**
     * Evaluates the assertion against the output.
     *
     * @param output the LLM output to check
     * @return true if assertion passes, false otherwise
     * @throws Exception if evaluation fails
     */
    boolean evaluate(String output) throws Exception;

    /**
     * Gets a human-readable description of this assertion.
     *
     * @return description string
     */
    String getDescription();

    /**
     * Gets the assertion type name (e.g., "equals", "contains", "regex").
     *
     * @return type name
     */
    String getType();
}
