# Paragon Quick Start Guide

## Installation

### Prerequisites
- Java 17 or higher
- Maven 3.8+

### Build from Source
```bash
git clone https://github.com/agrawalisha1703-spec/paragon.git
cd paragon
mvn clean package
```

## Running Tests

### Quick Test
```bash
java -jar target/paragon-cli-0.1.0-jar-with-dependencies.jar
```
This runs the default sample test: `sample-tests/invoice-generation.yaml`

### Custom Test File
```bash
java -jar target/paragon-cli-0.1.0-jar-with-dependencies.jar your-tests.yaml
```

## Writing Your First Test

Create a file `my-tests.yaml`:

```yaml
provider: mock

tests:
  - name: "My First Test"
    prompt: "Generate a greeting"
    assertions:
      - type: contains
        value: "hello"
      - type: regex
        value: "^[A-Z]"  # Starts with uppercase
```

Run it:
```bash
java -jar target/paragon-cli-0.1.0-jar-with-dependencies.jar my-tests.yaml
```

## Test Assertions

### Equals - Exact String Match
```yaml
assertions:
  - type: equals
    value: "exact expected output"
```

### Contains - Substring Search
```yaml
assertions:
  - type: contains
    value: "expected substring"
```

### Regex - Pattern Matching
```yaml
assertions:
  - type: regex
    value: "^[0-9]{3}-[0-9]{4}$"  # Matches 123-4567
```

### IsValidJson - JSON Validation
```yaml
assertions:
  - type: isValidJson
```

### JsonSchema - Schema Validation
```yaml
assertions:
  - type: jsonSchema
    schema: "schemas/invoice.json"
```

## Running Unit Tests

```bash
mvn clean test
```

**Result:** 99/99 tests PASSING ✅

## Generating Coverage Report

```bash
mvn clean test
open target/site/jacoco/index.html
```

**Coverage:** 42.6% instruction coverage

## Project Structure

```
paragon/
├── src/main/java/io/paragon/cli/
│   ├── ManualTestRunner.java          # CLI entry point
│   ├── assertion/                     # 5 assertion types
│   ├── loader/                        # YAML parsing
│   ├── model/                         # Data models
│   ├── provider/                      # LLM providers
│   ├── report/                        # Reporting
│   └── runner/                        # Test orchestration
├── src/test/java/io/paragon/cli/
│   └── [8 test files with 99 tests]
├── sample-tests/
│   ├── invoice-generation.yaml
│   └── comprehensive-tests.yaml
├── pom.xml                            # Maven configuration
└── README.md                          # Full documentation
```

## Example Test File

See `sample-tests/comprehensive-tests.yaml` for a complete example with all assertion types.

## Next Steps

1. Write your own test files in YAML
2. Run with the CLI tool
3. Integrate into your CI/CD pipeline
4. Check the [README.md](README.md) for advanced usage

## Support

- **GitHub Issues:** [Report bugs](https://github.com/agrawalisha1703-spec/paragon/issues)
- **GitHub Discussions:** [Ask questions](https://github.com/agrawalisha1703-spec/paragon/discussions)
- **Documentation:** See [README.md](README.md) for detailed guide

---

**Made for regression testing LLM prompts** ❤️
