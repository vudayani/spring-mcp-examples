package com.example.springmcp.sample;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.client.transport.StdioClientTransport;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GithubLensApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubLensApplication.class, args);
	}

	@Bean
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder, List<McpFunctionCallback> functionCallbacks) {
		return chatClientBuilder.defaultFunctions(functionCallbacks.toArray(new McpFunctionCallback[0])).build();
	}

	@Bean
	List<McpFunctionCallback> functionCallbacks(McpSyncClient githubMcpClient, McpSyncClient slackMcpClient) {

		var githubCallbacks = githubMcpClient.listTools(null).tools().stream()
				.map(tool -> new McpFunctionCallback(githubMcpClient, tool)).toList();

		var slackCallbacks = slackMcpClient.listTools(null).tools().stream()
				.map(tool -> new McpFunctionCallback(slackMcpClient, tool)).toList();

		return Stream.concat(githubCallbacks.stream(), slackCallbacks.stream()).map(callback -> {
			try {
				return callback;
			} catch (Exception e) {
				System.err.println("Error registering tool: " + e.getMessage());
				return null;
			}
		}).filter(Objects::nonNull).toList();
	}

	@Bean(destroyMethod = "close")
	McpSyncClient githubMcpClient() {

		// based on https://github.com/modelcontextprotocol/servers/tree/main/src/github
		var githubMcpClient = ServerParameters.builder("npx").args("-y", "@modelcontextprotocol/server-github")
				.addEnvVar("GITHUB_PERSONAL_ACCESS_TOKEN", System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN")).build();

		var mcpClient = McpClient.using(new StdioClientTransport(githubMcpClient))
				.requestTimeout(Duration.ofSeconds(10)).sync();

		var init = mcpClient.initialize();

		System.out.println("MCP github client initialized: " + init);

		return mcpClient;

	}

	@Bean(destroyMethod = "close")
	McpSyncClient slackMcpClient() {

		// based on https://github.com/modelcontextprotocol/servers/tree/main/src/slack
		var slackParams = ServerParameters.builder("npx").args("-y", "@modelcontextprotocol/server-slack")
				.addEnvVar("SLACK_BOT_TOKEN", System.getenv("SLACK_BOT_TOKEN"))
				.addEnvVar("SLACK_TEAM_ID", System.getenv("SLACK_TEAM_ID")).build();

		var mcpClient = McpClient.using(new StdioClientTransport(slackParams)).requestTimeout(Duration.ofSeconds(10))
				.sync();

		var init = mcpClient.initialize();

		System.out.println("MCP slack serveriInitialized: " + init);

		return mcpClient;

	}

}
