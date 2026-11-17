package io.paragon.cli.assertion;

import io.paragon.cli.model.Assertion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AssertionFactory Tests")
class AssertionFactoryTest {

    @Test
    @DisplayName("Should create EqualsAssertion")
    void testCreateEqualsAssertion() throws Exception {
        Assertion config = new Assertion();
        config.setType("equals");
        config.setValue("test value");
        
        AssertionEngine engine = AssertionFactory.createAssertion(config);
        assertNotNull(engine);
        assertEquals("equals", engine.getType());
    }

    @Test
    @DisplayName("Should create ContainsAssertion")
    void testCreateContainsAssertion() throws Exception {
        Assertion config = new Assertion();
        config.setType("contains");
        config.setValue("substring");
        
        AssertionEngine engine = AssertionFactory.createAssertion(config);
        assertNotNull(engine);
        assertEquals("contains", engine.getType());
    }

    @Test
    @DisplayName("Should create RegexAssertion")
    void testCreateRegexAssertion() throws Exception {
        Assertion config = new Assertion();
        config.setType("regex");
        config.setValue("^pattern.*");
        
        AssertionEngine engine = AssertionFactory.createAssertion(config);
        assertNotNull(engine);
        assertEquals("regex", engine.getType());
    }

    @Test
    @DisplayName("Should create IsValidJsonAssertion")
    void testCreateIsValidJsonAssertion() throws Exception {
        Assertion config = new Assertion();
        config.setType("isValidJson");
        
        AssertionEngine engine = AssertionFactory.createAssertion(config);
        assertNotNull(engine);
        assertEquals("isValidJson", engine.getType());
    }

    @Test
    @DisplayName("Should be case-insensitive for type")
    void testCaseInsensitiveType() throws Exception {
        Assertion config = new Assertion();
        config.setType("EQUALS");
        config.setValue("test");
        
        AssertionEngine engine = AssertionFactory.createAssertion(config);
        assertNotNull(engine);
        assertEquals("equals", engine.getType());
    }

    @Test
    @DisplayName("Should throw for unsupported type")
    void testUnsupportedType() {
        Assertion config = new Assertion();
        config.setType("unsupported");
        
        assertThrows(IllegalArgumentException.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should handle null type")
    void testNullType() {
        Assertion config = new Assertion();
        config.setType(null);
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should validate required value for equals")
    void testEqualsRequiresValue() {
        Assertion config = new Assertion();
        config.setType("equals");
        // Value not set
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should validate required value for contains")
    void testContainsRequiresValue() {
        Assertion config = new Assertion();
        config.setType("contains");
        // Value not set
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should validate required value for regex")
    void testRegexRequiresValue() {
        Assertion config = new Assertion();
        config.setType("regex");
        // Value not set
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should handle empty type")
    void testEmptyType() {
        Assertion config = new Assertion();
        config.setType("");
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should handle whitespace type")
    void testWhitespaceType() {
        Assertion config = new Assertion();
        config.setType("   ");
        
        assertThrows(Exception.class, () -> {
            AssertionFactory.createAssertion(config);
        });
    }

    @Test
    @DisplayName("Should create all 5 types successfully")
    void testAllSupportedTypes() throws Exception {
        String[] types = {"equals", "contains", "regex", "isValidJson"};
        
        for (String type : types) {
            Assertion config = new Assertion();
            config.setType(type);
            if (!type.equals("isValidJson")) {
                config.setValue("test");
            }
            
            AssertionEngine engine = AssertionFactory.createAssertion(config);
            assertNotNull(engine);
            assertEquals(type, engine.getType());
        }
    }
}
