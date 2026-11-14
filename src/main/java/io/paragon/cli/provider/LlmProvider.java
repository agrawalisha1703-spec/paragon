package io.paragon.cli.provider;

/**
 * Represents an LLM provider that generates prompt outputs.
 * Different implementations (mock, OpenAI, Anthropic, etc.) can be plugged in.
 */
public interface LlmProvider {
    /**
     * Calls the LLM with a prompt and returns the output.
     *
     * @param prompt the prompt to send to the LLM
     * @return the LLM's output
     * @throws Exception if the call fails
     */
    String call(String prompt) throws Exception;
}
