package com.example.springmcp.sample;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailySummaryJob {

	@Autowired
	private ChatClient chatClient;
	
	@Value("classpath:/prompts/dailySummaryPrompt.st")
    private Resource dailySummaryPrompt;
	
	@Value("classpath:/prompts/actionItemsPrompt.st")
    private Resource actionItemsPrompt;
	
	@Scheduled(cron = "0 0 8 * * *")
	public void fetchAndSendGitSummary() {
		try {
			chatClient
				.prompt()
				.user(u -> u.text(dailySummaryPrompt)
					.params(Map.of("repoName", "blogging-platform", "repoOwner", "venkat-vmv")))
				.call().content();
		} catch (Exception e) {
			System.err.println("Error fetching GitHub Summary for repository: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 0 8 * * *")
	public void fetchActionItemsAndSendRemainders() {
		try {
			System.out.println(chatClient.
					prompt()
					.user(u -> u.text(actionItemsPrompt)
							.params(Map.of("repoName","blogging-platform", "repoOwner", "venkat-vmv")))
					.call().content());
		} catch (Exception e) {
			System.err.println("Error fetching github action items: " + e.getMessage());
			e.printStackTrace();
		}
	}

}