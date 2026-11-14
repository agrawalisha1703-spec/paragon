# Changelog

All notable changes to Paragon will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial project setup and repository infrastructure
- Core domain models (TestSuite, TestCase, Assertion)
- YAML test format support
- Mock LLM provider with deterministic responses
- Assertion implementations: equals, contains, regex, isValidJson, jsonSchema
- Console and JUnit-XML reporters
- CLI tool with Picocli framework
- GitHub Actions CI/CD pipeline
- Comprehensive documentation (README, CONTRIBUTING, CODE_OF_CONDUCT)
- Unit tests and integration tests (~70% coverage)

### Changed
- N/A

### Fixed
- N/A

### Removed
- N/A

## [0.1.0] - 2026-12-31

### Added
- **Initial release:** Paragon v0.1.0
- CLI tool for running prompt regression tests
- YAML-based test suite format
- Mock LLM provider (deterministic, free)
- 5 core assertion types:
  - `equals` — exact string match
  - `contains` — substring check
  - `regex` — regex pattern matching
  - `isValidJson` — JSON validation
  - `jsonSchema` — JSON schema validation
- Console reporter (human-readable output)
- JUnit-XML reporter (CI/CD integration)
- Exit-code contract (non-zero on failure)
- Maven Central distribution
- Full Apache 2.0 license and governance files
- GitHub Actions CI/CD automation

### Documentation
- README with problem statement, installation, quick start
- CONTRIBUTING.md with development workflow
- CODE_OF_CONDUCT.md (Contributor Covenant)
- Comprehensive Javadoc for core classes

---

## Future Roadmap

### [0.2.0] - Planned
- Real LLM providers: OpenAI, Anthropic, Google
- Environment variable support for API keys
- Advanced assertions: semantic similarity, LLM-as-judge
- Test result caching for performance
- Logging and debug modes

### [0.3.0] - Planned
- React dashboard for test visualization
- Test run history and trends
- GitHub Action marketplace release
- GraalVM native binary compilation
- Support for environment files (.env)

### [0.4.0+] - Planned
- Multi-language client libraries (Python, Node.js, Go)
- API server mode for distributed testing
- Test analytics and metrics
- Community plugin system
