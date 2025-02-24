package com.example.springmcp.sample;

import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
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
			ToolCallbackProvider tools, ConfigurableApplicationContext context) {

		return args -> {
			var chatClient = chatClientBuilder
					.defaultTools(tools)
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

}
