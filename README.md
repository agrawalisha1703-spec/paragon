# Paragon — Regression Testing for LLM Prompts

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Java 17+](https://img.shields.io/badge/Java-17+-green.svg)]()

## What is Paragon?

Paragon is an open-source regression-testing framework for LLM (Large Language Model) prompts. It brings automated testing discipline to prompt engineering, catching silent failures in production before they happen.

### The Problem

When you use ChatGPT, Claude, or other LLMs in production:
- You write a prompt that generates JSON, emails, or other structured data
- The LLM gives you the expected output
- **Then:** The model upgrades, you tweak the prompt, or the provider changes behavior
- **Result:** Output breaks (JSON becomes text, required fields disappear)
- **Nobody notices** until customers complain — silent failure in production ❌

### The Solution

Paragon lets you write **automated tests in simple YAML** to catch these breaks in CI/CD before they reach production.

```yaml
# tests.yaml
provider: mock
tests:
  - name: "Generate invoice JSON"
    prompt: "Generate invoice JSON with fields: id, total, date"
    assertions:
      - type: isValidJson
      - type: contains
        value: "id"
```

Run in CI/CD:
```bash
$ paragon run tests.yaml
✓ Generate invoice JSON (2 assertions passed)
PASS: 1/1 tests
Exit code: 0
```

---

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

### Run Tests

```bash
# Run all tests (106 tests: 99 unit + 7 integration)
mvn clean test

# View code coverage report
open target/site/jacoco/index.html
```

**Test Status:** ✅ 106/106 tests passing

### Run

```bash
java -jar target/paragon-cli-0.1.0-jar-with-dependencies.jar run tests.yaml
```

---

## Quick Start

### 1. Create a test file (`tests.yaml`)

```yaml
provider: mock

tests:
  - name: "Test JSON generation"
    prompt: "Generate a greeting message as JSON with fields: message, timestamp"
    assertions:
      - type: isValidJson
      - type: contains
        value: "message"
      - type: contains
        value: "timestamp"

  - name: "Test text generation"
    prompt: "Generate a short greeting"
    assertions:
      - type: contains
        value: "hello"
```

### 2. Run tests

```bash
java -jar paragon-cli-0.1.0-jar-with-dependencies.jar run tests.yaml
```

### 3. View results

```
Test Results:
✓ Test JSON generation (3/3 assertions passed)
✓ Test text generation (1/1 assertions passed)

PASS: 2/2 tests passed
Exit code: 0
```

---

## Assertion Types

Paragon supports the following assertion types:

| Assertion | Description | Example |
|---|---|---|
| `equals` | Exact string match | `equals: "expected output"` |
| `contains` | Substring check | `contains: "json"` |
| `regex` | Regex pattern match | `regex: "^[A-Z]"` |
| `isValidJson` | Validates JSON structure | `isValidJson: true` |
| `jsonSchema` | Validates against JSON schema | `jsonSchema: "schema.json"` |

---

## Use Cases

- **Regression testing** when LLM provider upgrades
- **Prompt iteration** with confidence (changes won't break systems)
- **Output validation** (JSON, XML, CSV structures guaranteed)
- **Multi-provider fallback** testing
- **Cost monitoring** (test with free mock before calling expensive APIs)

---

## Contributing

We welcome contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for how to get started.

**Code of Conduct:** See [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

---

## License

Paragon is licensed under the [Apache License 2.0](LICENSE).

---

## Roadmap

- **v0.1.0** (Current): CLI, YAML tests, mock provider, 5 assertion types, CI integration
- **v0.2.0** (Planned): Real LLM providers (OpenAI, Anthropic), advanced assertions
- **v0.3.0+** (Future): React dashboard, GraalVM native binary, GitHub Action marketplace

---

## Contact & Support

- **GitHub Issues:** [Report bugs or request features](https://github.com/agrawalisha1703-spec/paragon/issues)
- **Discussions:** [Ask questions or discuss ideas](https://github.com/agrawalisha1703-spec/paragon/discussions)

---

**Made with ❤️ for the AI/ML community**
