package com.example.springmcp.sample;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
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
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
		return chatClientBuilder
				.defaultTools(tools)
				.build();
	}

}
