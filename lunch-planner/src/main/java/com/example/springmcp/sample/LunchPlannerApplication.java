package com.example.springmcp.sample;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.transport.ServerParameters;
import org.springframework.ai.mcp.client.transport.StdioClientTransport;
import org.springframework.ai.mcp.spring.McpFunctionCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LunchPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunchPlannerApplication.class, args);
	}

	@Bean
	CommandLineRunner predefinedQuestions(ChatClient.Builder chatClientBuilder,
			List<McpFunctionCallback> functionCallbacks, ConfigurableApplicationContext context) {

		return args -> {
			var chatClient = chatClientBuilder.defaultFunctions(functionCallbacks.toArray(new McpFunctionCallback[0]))
					.build();

			System.out.println("Running predefined questions with AI model responses:\n");

			String question1 = "Find a vegetarian-friendly restaurant within 2 miles of my office address (MG Road, bangalore). Then let everyone in #lunch-plans on Slack know we’re meeting there at 12:30 PM.";
			System.out.println("QUESTION: " + question1);
			System.out.println("ASSISTANT: " + chatClient.prompt(question1).call().content());

			context.close();

		};
	}

	@Bean
	List<McpFunctionCallback> functionCallbacks(McpSyncClient googleMapMcpClient, McpSyncClient slackMcpClient) {

		var googleMapCallbacks = googleMapMcpClient.listTools(null).tools().stream()
				.map(tool -> new McpFunctionCallback(googleMapMcpClient, tool)).toList();

		var slackCallbacks = slackMcpClient.listTools(null).tools().stream()
				.map(tool -> new McpFunctionCallback(slackMcpClient, tool)).toList();

		return Stream.concat(googleMapCallbacks.stream(), slackCallbacks.stream()).toList();
	}

	@Bean(destroyMethod = "close")
	McpSyncClient googleMapMcpClient() {

		// based on
		// https://github.com/modelcontextprotocol/servers/tree/main/src/google-maps
		var googleMapsParams = ServerParameters.builder("npx").args("-y", "@modelcontextprotocol/server-google-maps")
				.addEnvVar("GOOGLE_MAPS_API_KEY", System.getenv("GOOGLE_MAPS_API_KEY")).build();

		var mcpClient = McpClient.using(new StdioClientTransport(googleMapsParams))
				.requestTimeout(Duration.ofSeconds(10)).sync();

		var init = mcpClient.initialize();

		System.out.println("MCP Initialized: " + init);

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

		System.out.println("MCP Initialized: " + init);

		return mcpClient;

	}

}
