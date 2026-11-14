package io.paragon.cli.assertion;

import io.paragon.cli.model.Assertion;

/**
 * Factory for creating assertion instances based on assertion type.
 * Implements the Strategy pattern for pluggable assertion engines.
 */
public class AssertionFactory {
    /**
     * Creates an AssertionEngine based on assertion configuration.
     *
     * @param assertion the assertion model
     * @return the appropriate AssertionEngine implementation
     * @throws IllegalArgumentException if assertion type is not supported
     */
    public static AssertionEngine createAssertion(Assertion assertion) throws Exception {
        if (assertion == null) {
            throw new IllegalArgumentException("Assertion cannot be null");
        }

        String type = assertion.getType();
        
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Assertion type is required");
        }

        type = type.toLowerCase().trim();

        switch (type) {
            case "equals":
                if (assertion.getValue() == null) {
                    throw new IllegalArgumentException("'equals' assertion requires 'value' parameter");
                }
                return new EqualsAssertion(assertion.getValue());

            case "contains":
                if (assertion.getValue() == null) {
                    throw new IllegalArgumentException("'contains' assertion requires 'value' parameter");
                }
                return new ContainsAssertion(assertion.getValue());

            case "regex":
                if (assertion.getValue() == null) {
                    throw new IllegalArgumentException("'regex' assertion requires 'value' parameter");
                }
                return new RegexAssertion(assertion.getValue());

            case "isvalidjson":
                return new IsValidJsonAssertion();

            case "jsonschema":
                if (assertion.getSchema() == null) {
                    throw new IllegalArgumentException("'jsonSchema' assertion requires 'schema' parameter");
                }
                return new JsonSchemaAssertion(assertion.getSchema());

            default:
                throw new IllegalArgumentException("Unsupported assertion type: " + type);
        }
    }
}
