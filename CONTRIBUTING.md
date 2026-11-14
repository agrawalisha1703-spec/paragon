# Contributing to Paragon

Thank you for your interest in contributing to Paragon! We welcome contributions of all kinds, including code, documentation, bug reports, and feature requests.

## Code of Conduct

Please read and follow our [Code of Conduct](CODE_OF_CONDUCT.md). We are committed to providing a welcoming and inclusive environment for all contributors.

---

## Getting Started

### Prerequisites

- **Java 17+** ([Download](https://adoptium.net/))
- **Maven 3.8+** ([Download](https://maven.apache.org/download.cgi))
- **Git**

### Setting Up Your Development Environment

1. **Fork the repository** on GitHub
2. **Clone your fork:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/paragon.git
   cd paragon
   ```

3. **Add upstream remote:**
   ```bash
   git remote add upstream https://github.com/agrawalisha1703-spec/paragon.git
   ```

4. **Install dependencies and build:**
   ```bash
   mvn clean install
   ```

5. **Run tests:**
   ```bash
   mvn test
   ```

---

## Development Workflow

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

Use descriptive branch names:
- `feature/add-semantic-similarity-assertion`
- `fix/yaml-loader-null-pointer`
- `docs/update-readme-examples`

### 2. Make Your Changes

- Follow [Java conventions](https://www.oracle.com/java/technologies/javase/codeconventions-136091.html)
- Keep commits atomic and well-described
- Write tests for new functionality
- Run tests before committing: `mvn test`

### 3. Commit with Clear Messages

```bash
git commit -m "Add regex assertion type

- Implements regex matching for prompt outputs
- Adds RegexAssertion class with pattern compilation
- Includes 5 test cases for common patterns
- Updates YAML schema documentation
"
```

### 4. Push and Create a Pull Request

```bash
git push origin feature/your-feature-name
```

Then open a Pull Request on GitHub with a clear description of your changes.

---

## Code Style & Standards

### Java Code Style

- **Formatting:** 4-space indentation
- **Naming:** camelCase for variables/methods, PascalCase for classes
- **Javadoc:** Public methods and classes should have documentation
- **Imports:** Organize alphabetically, no wildcard imports

Example:
```java
/**
 * Validates that output contains a substring.
 */
public class ContainsAssertion implements Assertion {
    private final String expected;

    public ContainsAssertion(String expected) {
        this.expected = expected;
    }

    @Override
    public boolean evaluate(String output) {
        return output.contains(expected);
    }
}
```

### Testing Standards

- **Test class naming:** `[ClassName]Test.java`
- **Test method naming:** `testMethodName_scenario_expectedResult()`
- **Coverage target:** Aim for >70% code coverage
- **Framework:** JUnit 5 + Mockito

Example:
```java
@Test
void testEvaluate_containsSubstring_returnsTrue() {
    Assertion assertion = new ContainsAssertion("invoice");
    String output = "Generated invoice JSON";
    
    assertTrue(assertion.evaluate(output));
}
```

---

## File Structure

```
paragon/
├── src/main/java/io/paragon/cli/
│   ├── model/           # Domain models (TestSuite, TestCase, Assertion)
│   ├── loader/          # YAML/JSON parsing
│   ├── provider/        # LLM providers (mock, openai, anthropic)
│   ├── assertion/       # Assertion implementations
│   ├── runner/          # Test execution engine
│   ├── report/          # Output formatters (console, junit-xml)
│   └── command/         # Picocli CLI commands
├── src/test/java/       # Unit tests mirroring main structure
├── docs/                # Additional documentation
├── pom.xml             # Maven configuration
├── README.md           # Project overview
├── CONTRIBUTING.md     # This file
└── LICENSE             # Apache 2.0
```

---

## Types of Contributions

### Bug Reports

Found a bug? Open an [issue](https://github.com/agrawalisha1703-spec/paragon/issues) with:
- Clear title describing the bug
- Steps to reproduce
- Expected vs. actual behavior
- Java/Maven versions
- Error logs or stack traces

### Feature Requests

Have an idea? Open an [issue](https://github.com/agrawalisha1703-spec/paragon/issues) with:
- Clear title
- Use case and motivation
- Proposed implementation (if you have ideas)
- Related issues or discussions

### Documentation

Improvements to README, guides, or examples are always welcome! Submit a PR with:
- Clear description of changes
- Proof-read for grammar/clarity

### Code Contributions

Contributing code? Follow the workflow above and ensure:
- [ ] Tests pass: `mvn test`
- [ ] Code follows style guidelines
- [ ] Commit messages are clear
- [ ] PR description explains *why* the change is needed
- [ ] Related issues are linked

---

## Testing Your Changes

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn test -Dtest=ContainsAssertionTest
```

### Check Code Coverage

```bash
mvn clean test jacoco:report
# Report generated at: target/site/jacoco/index.html
```

---

## Commit Message Guidelines

Use clear, descriptive commit messages:

```
[Type] Brief description (50 chars max)

Detailed explanation of the change (if needed).
- Bullet points for multiple changes
- Reference issues: Fixes #123

Signed-off-by: Your Name <your.email@example.com>
```

**Types:** `feat`, `fix`, `docs`, `test`, `refactor`, `chore`, `ci`

---

## Pull Request Guidelines

1. **Title:** Clear, descriptive title
2. **Description:** Explain *what* and *why*
3. **Related Issues:** Link with `Fixes #123` or `Related to #456`
4. **Checklist:**
   ```markdown
   - [ ] Tests pass
   - [ ] Code follows style guidelines
   - [ ] Documentation updated
   - [ ] Commit messages are clear
   ```

---

## Review Process

- **At least one maintainer review** before merge
- **All tests must pass** (CI/CD automated checks)
- **No merge conflicts**
- Constructive feedback is a normal part of the process!

---

## Recognition

Contributors will be recognized in:
- `CONTRIBUTORS.md` file
- GitHub repository contributors page
- Release notes for significant contributions

---

## Questions?

- **Open a discussion:** [GitHub Discussions](https://github.com/agrawalisha1703-spec/paragon/discussions)
- **Check existing issues** for similar questions
- **Ask in a PR comment** if you get stuck

---

**Thank you for contributing to Paragon! 🎉**
