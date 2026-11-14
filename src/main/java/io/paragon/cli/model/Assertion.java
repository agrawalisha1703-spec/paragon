package io.paragon.cli.model;

/**
 * Represents a single assertion within a test case.
 * Assertions define what conditions the LLM output must satisfy.
 */
public class Assertion {
    private String type;      // equals, contains, regex, isValidJson, jsonSchema
    private String value;     // For type-specific values (e.g., substring for contains)
    private String schema;    // For jsonSchema type

    public Assertion() {
    }

    public Assertion(String type) {
        this.type = type;
    }

    public Assertion(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "Assertion{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", schema='" + schema + '\'' +
                '}';
    }
}
