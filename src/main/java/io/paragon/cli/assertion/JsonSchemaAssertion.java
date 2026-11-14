package io.paragon.cli.assertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Assertion that validates output against a JSON schema.
 * Output must conform to the specified JSON schema file.
 */
public class JsonSchemaAssertion implements AssertionEngine {
    private final String schemaPath;
    private final JsonSchema schema;
    private final ObjectMapper objectMapper;

    public JsonSchemaAssertion(String schemaPath) throws IOException {
        this.schemaPath = schemaPath;
        this.objectMapper = new ObjectMapper();
        this.schema = loadSchema(schemaPath);
    }

    /**
     * Loads the JSON schema from file.
     *
     * @param schemaPath path to the schema file
     * @return loaded JsonSchema
     * @throws IOException if schema cannot be read
     */
    private JsonSchema loadSchema(String schemaPath) throws IOException {
        File schemaFile = new File(schemaPath);
        
        if (!schemaFile.exists()) {
            throw new IOException("Schema file not found: " + schemaPath);
        }

        try {
            JsonNode schemaNode = objectMapper.readTree(schemaFile);
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            return factory.getSchema(schemaNode);
        } catch (IOException e) {
            throw new IOException("Failed to load schema from " + schemaPath + ": " + e.getMessage(), e);
        }
    }

    @Override
    public boolean evaluate(String output) {
        if (output == null || output.trim().isEmpty()) {
            return false;
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(output);
            Set<ValidationMessage> errors = schema.validate(jsonNode);
            return errors.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Output must conform to JSON schema: " + schemaPath;
    }

    @Override
    public String getType() {
        return "jsonSchema";
    }

    @Override
    public String toString() {
        return "JsonSchemaAssertion{" + "schemaPath='" + schemaPath + '\'' + '}';
    }
}
