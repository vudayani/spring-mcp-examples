package com.example.springmcp.sample;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
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
			
			var scanner = new Scanner(System.in);
			System.out.println("\nWelcome to the Lunch Planner Assistant! I’m here to help you plan your lunch outing and notify your team. Provide a location, any food preferences, and the Slack channel to post to. Type 'exit' to end the session at any time.");

			try {
				while (true) {
					System.out.print("\nUser: ");
					String input = scanner.nextLine();

					if (input.equalsIgnoreCase("exit")) {
						System.out.println("Ending the chat session.");
						break;
					}

					System.out.print("Assistent: ");
					System.out.println(chatClient.prompt(input).call().content());
				}
			} finally {
				scanner.close();
				context.close();
			}

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
