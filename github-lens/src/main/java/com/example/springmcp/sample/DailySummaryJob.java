package com.example.springmcp.sample;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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

	@Scheduled(cron = "0 */1 * * * *")
	public void fetchAndSendGitSummary() {
		try {
			System.out.println(chatClient.
					prompt().system(s -> s.text(dailySummaryPrompt).params(Map.of("repoName","blogging-platform", "repoOwner", "venkat-vmv")))
					.advisors(new SimpleLoggerAdvisor()).call().content());
		} catch (Exception e) {
			System.err.println("Error fetching daily github summary: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 */2 * * * *")
	public void fetchActionItemsAndSendRemainders() {
		try {
			System.out.println(chatClient.
					prompt().system(s -> s.text(actionItemsPrompt).params(Map.of("repoName","blogging-platform", "repoOwner", "venkat-vmv")))
					.advisors(new SimpleLoggerAdvisor()).call().content());
		} catch (Exception e) {
			System.err.println("Error fetching github action items: " + e.getMessage());
			e.printStackTrace();
		}
	}
}