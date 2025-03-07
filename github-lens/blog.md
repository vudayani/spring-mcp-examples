# Model Context Protocol: Unlocking AI’s Full Potential with Spring AI

The Model Context Protocol (MCP) is revolutionizing AI integration by enabling Large Language Models (LLMs) to seamlessly connect with tools, data sources, and workflows. Developed by Anthropic, MCP eliminates the need for custom integrations by providing a standardized and composable way for AI applications to fetch real-time, context-aware information.

In this blog, we’ll explore:

- What MCP is and why it's gaining so much traction
- How Spring AI has adopted MCP to simplify AI-driven development
- A practical GitHub Lens example demonstrating MCP’s capabilities in action using Spring AI MCP

## What is Model Context Protocol?

The Model Context Protocol (MCP) is an open standard designed with the goal of addressing a fundamental challenge in AI Space: enabling LLMs to interact seamlessly with external tools, data sources, and systems without requiring custom-built integrations. 
Instead of manually wiring integrations and data retrieval logic, developers can use MCP to create plug-and-play AI systems that adapt to changing needs effortlessly.

## Why MCP?

In the rapidly evolving field of artificial intelligence, LLMs are only as good as the data they have access to. Traditionally, integrating LLMs with external tools and data has been a challenge. Let's say you’re building an AI-powered assistant for your organization that needs to:

- Fetch product details from database
- Access internal tools like CRMs or support ticket systems
- Provide real-time reviews, analysis on products and send notifications

Sounds simple enough, right? But here’s where the challenge arises: Each system has its own APIs and formats, making integration complex and time-consuming. To get this data to the LLM, you’d need to write custom integrations and update them constantly as your systems evolve. 

MCP eliminates these bottlenecks by providing a universal interface between LLMs and external systems.

## How MCP Works?

Here’s how it works:

**MCP Servers:** MCP Servers expose data, tools, and prompts in a standardized way that LLMs can understand.  With tools, LLMs can not only retrieve information from databases, APIs, or repositories but also trigger actions—such as creating a support ticket, sending a notification, or executing a workflow

**MCP Clients:** Intermediaries that connect LLMs to MCP servers, facilitating seamless communication

**LLM Decision-Making:** LLMs dynamically determine which tools or data sources to use based on the task at hand. This eliminates the need for hardcoded integrations, making AI-driven workflows more adaptable and scalable.

With MCP, AI applications gain real-time, context-aware intelligence without the complexity of custom integrations.

## Spring AI's Adoption of MCP

Spring AI has swiftly adopted the Model Context Protocol (MCP), making it easier for developers to integrate LLMs with diverse data sources and services. 
Instead of manually managing multiple API calls or writing custom integrations, Spring AI MCP abstracts away this complexity, allowing LLMs to interact seamlessly with external tools — without requiring low-level logic.

This bridges the gap between Generative AI and real-time data, making it much simpler to create intelligent, context-aware applications.
With Spring AI MCP, developers can focus on business logic while the framework efficiently manages system interactions.

To understand this better, let's start with the basics — building a simple Spring AI client. This will provide a foundation before we progressively integrate MCP tools to simplify external data fetching and automation.

## Setting Up a Basic Spring AI Client

We'll begin by setting up a simple Spring AI client that interacts with an LLM.

### 1. Adding Dependencies

Include the necessary Spring AI dependencies and maven repository configuration in your `pom.xml`:

```xml
<!-- The spring-ai-bom ensures consistent and compatible versions for all Spring AI dependencies -->
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.ai</groupId>
			<artifactId>spring-ai-bom</artifactId>
			<version>1.0.0-SNAPSHOT</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
<!-- Spring AI integration with OpenAI for using OpenAI in the application -->
<dependency>
	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

Maven Repository Configuration

```xml
<repositories>
	<repository>
		<id>spring-milestones</id>
		<name>Spring Milestones</name>
		<url>https://repo.spring.io/libs-milestone-local</url>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
	</repository>
	<repository>
		<id>spring-snapshots</id>
		<name>Spring Snapshots</name>
		<url>https://repo.spring.io/snapshot</url>
		<releases>
			<enabled>false</enabled>
		</releases>
	</repository>
</repositories>
```

### 2.Configuring OpenAI

This project uses OpenAI via Spring AI. Ensure to fetch and set the `OPENAI_API_KEY` to authenticate and interact with the LLM service. Set the environment variable and update `application.yml`:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
           model: "gpt-4o"
```

### 3. Creating a Simple Chat Client

Now, let's create a basic ChatClient and submit a prompt:

```java
@Bean
public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
    return chatClientBuilder.build();
}

public void simplePrompt() {
    String response = chatClient
            .prompt()
            .user(u -> u.text("Tell me a joke"))
            .call()
            .content();
    System.out.println(response);
}
```
Run the application and see how the LLM responds with some engaging outputs.

## Fetching GitHub Activity Summary 

Now that we've built a basic chat client, let's take this a step further by leveraging AI to summarize real-world data — starting with GitHub activity tracking. We'll track recent commits, open pull requests, and issues for a repository and create a concise summary.

For this example, we’ll summarize the GitHub activity of the [blogging-platform](https://github.com/venkat-vmv/blogging-platform.git) repository.

#### How would we achieve this?

We would either manually fetch data from GitHub by authenticating and calling GitHub APIs or use an available API server that abstracts these operations. Regardless, we still need to:

- Identify the right APIs to call for fetching commits, pull requests, and issues
- Manually orchestrate API calls to fetch each piece of data separately and handle API requests
- Determine and pass required inputs like repository name, owner, date filters, etc., to each API request 
- Extract, structure, and feed the relevant data into prompts manually
- Maintain custom API logic as requirements evolve - If the way we summarize data changes (e.g, including PR reviewers or filtering issues by priority), we need to update our API calls accordingly

```java
public void generateGitHubSummary() {

	// Step 1: Fetch GitHub Data
    String commits = fetchCommits();
    String pullRequests = fetchPullRequests();
    String issues = fetchIssues();

	// Step 2: Generate Summary using LLM
    String response = chatClient
            .prompt()
            .user(u -> u.text("Summarize the following GitHub data:\nCommits: " + commits + "\nPRs: " + pullRequests + "\nIssues: " + issues))
            .call()
            .content();
    System.out.println(response);
}

private String fetchCommits() {
  // Authenticate to GitHub server with GitHub token
  // Use GitHub APIs to fetch commits for a repo and return
  // or Call a publically available Github server to fetch relavant data
}
```

This approach is manual, time-consuming, and difficult to scale, especially when dealing with multiple APIs and evolving data requirements.
Now, let’s see how MCP resolves these challenges by offering a standardized way for LLMs to fetch real-time data from external services without manual intervention.

## Integrating MCP Tools with Spring AI
Instead of manually orchestrating API calls to fetch and feed data into prompts, we configure MCP tools that dynamically fetch and process relevant data on demand. With Spring AI MCP, we no longer need to hardcode data-fetching logic.

Let’s explore how Spring AI MCP simplifies integration by leveraging Spring’s dependency injection and configuration management to seamlessly configure MCP clients and servers.

In this example, we will use the publicly available [GitHub MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/github), which acts as a bridge to the GitHub API. It enables repository management, file operations, and search functionality while exposing these features as MCP tools that the LLM can understand.

### 1. Prerequisites

Ensure you have [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) installed, then run the following command: 
```bash
npm install -g npx
```

npx (Node Package Execute) is used later to start up the GitHub MCP server locally. 

### 2. Add Spring AI MCP Client Dependency

Add the below Spring AI MCP Client dependency to your `pom.xml`:
```xml
<!-- Spring AI MCP client library for integrating with MCP servers -->	
<dependency>
	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-mcp-client-spring-boot-starter</artifactId>
</dependency>
```

### 3.Configuring MCP Clients

Spring AI MCP simplifies tool integration through a JSON configuration file.
The MCP client needs to start and communicate with the MCP server, which runs as a separate process on your local machine. This configuration tells the MCP client where the server is, how to start it, and what environment variables it needs.

Create `mcp-servers-config.json`:

```yaml
{
  "mcpServers": {
    "github": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-github"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "your-api-key",
      }
    },
  }
}
```

This tells the MCP client how to start the GitHub MCP server using npx, along with the required authentication details.

**Environment Variables:** The `GITHUB_PERSONAL_ACCESS_TOKEN` is required to authenticate and interact with Github APIs.
You can choose one of the following methods to set the environment variable:

Option 1: Define environment variable in `mcp-servers-config.json`
- This is useful when you want all configurations in one place
- Tokens are stored in the JSON file (be mindful of security best practices!)

Option 2: Export environment variable instead of storing in JSON
- This keeps sensitive credentials out of config files
- Set them globally using terminal commands:
```bash
	export GITHUB_PERSONAL_ACCESS_TOKEN=your_github_token
```

For detailed instructions on fetching your Github API token, refer to the [GitHub MCP Server documentation](https://github.com/modelcontextprotocol/servers/tree/main/src/github)

### 4. Link the JSON Configuration in application.yml 
To make Spring AI load this configuration, add the following to `application.yml`

```yaml
spring:
  ai:
    mcp:
      client:
        stdio:
          servers-configuration: classpath:mcp-servers-config.json
```

That is it, your client setup to communicate to Github server is ready. 

#### What’s Happening Under the Hood?
Spring AI automatically:
- Creates MCP clients for each configured server (GitHub in this case)
- Manages transport layer (stdio) and executes the MCP commands
- Passes required environment variables (example: GitHub API token)

With all the heavy lifting handled by Spring AI MCP, we can directly jump into building our AI-powered application.

### 4. Chat Client Integration
With our MCP clients setup, the next step is integrating them with Spring AI’s ChatClient. Lets update the ChatClient to call MCP tools dynamically.

```java
@Bean
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {
		return chatClientBuilder
				.defaultTools(tools)
				.build();
	}
```

#### Fully Automated Function Callbacks
Spring AI MCP Clients auto-discovers tools exposed by MCP servers and registers them as function callbacks. We simply pass these tools into the ChatClient using Spring's dependency injection.
The LLM now has access to all registered MCP tools, allowing it to dynamically decide when and how to invoke them, removing the need for developers to manually orchestrate these interactions.

**Note**: While tool calling seems like a LLM model responsibility, it is actually the client application that executes tool calls.
The LLM decides and requests a tool call and provides the necessary input arguments, but the application executes the tool call using the provided inputs, and returns the results.

This separation ensures secure execution, as the AI model never directly interacts with external APIs. It can only request actions, while the application maintains full control over execution.

### 5. Generating the Daily GitHub Summary

Now that we have everything set up — MCP clients, server configurations, tool callbacks, and the chatClient — it’s time to build our application logic.
Our goal is to generate a daily summary of GitHub activity. To achieve this, we define a scheduled task that sends a prompt to summarize the commits, pull requests, and issues of the [blogging-platform](https://github.com/venkat-vmv/blogging-platform) repository.

```java
@Scheduled(cron = "0 0 8 * * *") // Runs every day at 8:00 AM
public void fetchAndSendGitSummary() {
    try {
        String response = chatClient
                .prompt()
                .user(u -> u.text(dailySummaryPrompt)
                        .params(Map.of("repoName", "blogging-platform", "repoOwner", "venkat-vmv")))
                .call()
                .content();
		System.out.println(response);
    } catch (Exception e) {
        System.err.println("Error fetching daily GitHub summary: " + e.getMessage());
        e.printStackTrace();
    }
}
```

#### Defining the dailySummaryPrompt
The `dailySummaryPrompt` instructs the LLM to gather and format a GitHub activity summary:

```bash
Provide a daily summary of the GitHub repository {repoName} owned by {repoOwner}. The summary should include the following sections:

### **1. Recent Commits:**
   - Summarize latest commits made to the 'main' branch in the last 24 hours
   - For each commit, include:
     - Commit messages (with a Short description if available of the commit purpose)
     - Authors and date
     - Direct link to the commit  

### **2. Open Pull Requests:**
   - List all open pull requests, highlighting:
	  - Title with a direct link to the PR
	  - Author and date
	  - Short description** of the PR's purpose
	  - Number of comments and review comments within the PR
	  - Requested reviewers (if any)
	  - PR age (e.g., "Opened 5 days ago")
	  
   - Highlight PRs that need attention (if applicable):
      - PRs that have been open for more than 2 days
      - PRs with pending reviewer actions or unresolved comments
	  - Tag the reviewers/assignees to bring this to their attention

### **3. Open Issues:**
   - List high-priority open issues, prioritizing those labeled "release-blocker" or "high-priority"
   - Filter issues by type `is:issue`, `is:open`, `label:high-priority`, `label:release-block`
   - For each issue, include:
   	  - Title with a direct link to issue
	  - Labels or milestones
  	  - Brief description of the issue (if available) 

The summary should be concise, clear, and actionable to help the development team quickly understand the repository's status and priorities
```

### The Magic Behind the Scenes
Once the cron job runs, Spring AI and MCP take over the execution flow:

#### The LLM Receives the Prompt and Determines Tool Calls
Unlike traditional approaches where we manually orchestrate API calls, Spring AI lets the LLM decide what actions to take dynamically:

- The LLM receives the prompt along with the available tools embedded into the system prompt
- The LLM analyzes the prompt and determines which tools to invoke to fetch the required data:
	- Latest commits -> calls the `list_commits` tool
	- Open pull requests -> calls the `list_pull_requests` tool
	- Open issues -> calls the `list_issues` tool
- The LLM passes the necessary inputs and requests the application to execute these tools

#### MCP Clients Execute Tool Calls
The Spring AI MCP Client handles all communication with the GitHub MCP Server. The necessary tools are executed automatically and the fetched data is returned to the LLM.
The LLM then structures the results according to the original prompt’s instructions, preparing the summary in the desired format.

#### Below is the response from the LLM:
```bash
### Daily Summary for the GitHub Repository: **blogging-platform** by **venkat-vmv**

---
### **1. Recent Commits:**
1. **[Add search functionality for posts](https://github.com/venkat-vmv/blogging-platform/commit/931f2fbe6d02580f1c3883160f5eaa259b37a719)**
   - **Author:** [venkat-vmv](https://github.com/venkat-vmv)
   - **Date:** February 2, 2025
   - **Description:** Implemented search functionality for retrieving posts.

2. **[Update README.md (#9)](https://github.com/venkat-vmv/blogging-platform/commit/6256913f3eee64f1c3c2d8da770a6b3bbe5e00cd)**
   - **Author:** [ganith2001](https://github.com/ganith2001)
   - **Date:** February 2, 2025
   - **Description:** Updated the README file to reflect recent changes.

3. **[Create data.sql (#8)](https://github.com/venkat-vmv/blogging-platform/commit/93ba28c5ad5df6ba7ff4a1f1a2b07f30e1c3a111)**
   - **Author:** [ganith2001](https://github.com/ganith2001)
   - **Date:** February 2, 2025
   - **Description:** Created a SQL script for database initialization.

No new commits in the last 24 hours.
---

### **2. Open Pull Requests:**
1. **[add pagination](https://github.com/venkat-vmv/blogging-platform/pull/14)**
   - **Author:** [venkat-vmv](https://github.com/venkat-vmv)
   - **Date Created:** February 24, 2025
   - **Description:** Includes pagination support for retrieving posts.
   - **Comments:** 1
   - **Review Comments:** 0
   - **Reviewers:** None
   - **PR Age:** Opened 8 days ago

2. **[(bug-fix): Fix issue with deleting posts that have comments](https://github.com/venkat-vmv/blogging-platform/pull/11)**
   - **Author:** [vudayani](https://github.com/vudayani)
   - **Date Created:** February 2, 2025
   - **Description:** Change: Update the delete logic to remove comments before deleting posts.
   - **Comments:** 0
   - **Review Comments:** 0
   - **Reviewers:** [vudayani](https://github.com/vudayani)
   - **PR Age:** Opened less than 1 day ago

**PRs Needing Attention:**
- **[add pagination](https://github.com/venkat-vmv/blogging-platform/pull/14)** - Open for more than 2 days, please review.

---

### **3. Open Issues:**
1. **[Error when deleting a post that has comments](https://github.com/venkat-vmv/blogging-platform/issues/5)**
   - **Labels:** bug, release-blocker
   - **Description:** Deleting a post with associated comments throws a DataIntegrityViolationException.

2. **[Add search functionality for posts](https://github.com/venkat-vmv/blogging-platform/issues/4)**
   - **Labels:** high-priority
   - **Description:** Provide an API endpoint to search posts by title or content.
---

This summary provides an overview of the recent activity, open pull requests requiring attention, and issues of high priority that need to be addressed.
```

## Extending This Further: Adding Slack Integration
While we explored generating daily GitHub summaries, we can enhance this workflow by integrating Slack notifications. These concise GitHub summaries can be automatically delivered to the team's Slack channel, ensuring everyone stays informed about recent activity and pending action items.

### Steps to Integrate Slack with MCP

With zero code changes, we only need to:

- Configure the Slack MCP Server in the `mcp-servers-config.json` file
```yaml
{
  "mcpServers": {
    "github": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-github"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "your-api-key",
      }
    },
    "slack": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-slack"
      ], 
      "env": {
        "SLACK_BOT_TOKEN": "slack-bot-token",
        "SLACK_TEAM_ID": "slack-team-id"
      }
    }
  }
}
```

- Set Slack environment variables to authenticate and communicate with Slack
```bash
	export SLACK_BOT_TOKEN=your_slack_bot_token
	export SLACK_TEAM_ID=your_slack_team_id
```
For detailed instructions on fetching Slack token and Team Id, refer to the [Slack MCP Server documentation](https://github.com/modelcontextprotocol/servers/tree/main/src/slack)

- Fine-tune the prompt to format the summary properly for Slack

The execution flow remains the same, but we refine the `dailySummaryPrompt` to ensure well-structured messages for Slack. Add the following section to the existing prompt:

```bash
### **4. Formatting for Slack:**
   - Present the information in a structured and well-organized format
   - Use **bold headings** (`*Recent Commits*`)
   - Use **bullet points** for clarity
   - **Include direct links** for quick access
   - If a section has no updates, clearly indicate with:  
      *No new commits in the last 24 hours.*  
   - End with a gentle reminder for the team to review PRs or resolve critical issues and wishing a happy coding day
   - Structure the summary with sections, bullet points, bold headings or attachments for clear readability
   - Post the summary to the **'#dev-updates'** Slack channel
```

Once integrated, the final output is a well-structured Slack message posted automatically to the `#dev-updates` channel:

![Daily GitHub Summary Results](../github-lens/src/main/resources/images/github-summary-1.png)

Now, we saw how MCP is not just about retrieving data, it also enables executing actions through tools. In our Slack integration, we don’t just fetch data but we also invoke a tool to post the summarized GitHub activity directly to a Slack channel. This demonstrates actionable AI, where LLMs go beyond insights and trigger real-world operations seamlessly.

### Summary: What We Built with Spring AI MCP
We built an AI-powered assistant that keeps teams informed about GitHub repository activity and automates Slack notifications. 

Here’s an architecture diagram illustrating how different MCP components interact to get a better understanding:

![Github Summary Architecture](../github-lens/src/main/resources/images/github-lens-arch.png)

- The LLM interacts with MCP clients to fetch and process data dynamically
- The GitHub and Slack MCP clients communicate with their respective MCP Servers:
	- **[GitHub MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/github)**: Fetches repository data (pull requests, issues, commits)
	- **[Slack MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/slack)**: Posts summaries and notifications to Slack channels

#### Why This is Powerful
With just a few lines of code, we have built an AI-powered GitHub assistant that:

- Eliminates manual API orchestration - The LLM decides what tools to invoke
- Reduces boilerplate logic - Spring AI MCP automates tool execution and integration
- Enhances extensibility – New tools can be added by updating configurations, not modifying code
- Enables intelligent decision-making – The LLM automatically determines the data needed based on natural language instructions and how to retrieve it
- Handles real-time execution - Tools are invoked dynamically based on context, ensuring up-to-date information

This approach highlights the power of MCP and Spring AI, enabling developers to effortlessly build intelligent, automated workflows that adapt to changing requirements without additional code.

## Advanced Topics & Customization
Spring AI MCP offers additional customization in how clients communicate with servers that developers can leverage based your application needs. Let’s briefly explore these configurations.

### Choosing the Right MCP Client Type & Transport Option
Spring AI MCP provides flexibility in how clients communicate with servers. Based on your application’s requirements, you can choose the appropriate client type and transport mechanism.

#### **Multiple Transport Options in MCP**

MCP clients can communicate with servers using different transport mechanisms:

- STDIO (Default, Used in Our Example): Process-based communication between the application and the MCP server
- SSE (Server-Sent Events):  Ideal for event-driven applications requiring streaming responses
- Java HttpClient-based SSE transport: Suitable for high-performance streaming over HTTP
- Spring-Specific Transports:
	- WebFlux SSE -> Reactive HTTP streaming
	- WebMVC SSE -> Servlet-based HTTP streaming

Notice, we are using STDIO (`spring.ai.mcp.client.stdio`) in our Github examples as it is simple and works well for process-based interactions with local MCP servers.

#### **MCP Client Types: Synchronous vs. Asynchronous**

Spring AI MCP supports two types of clients, depending on whether your application needs blocking or non-blocking execution:

1. Synchronous Client (Default, Used in Our Example)
- Uses blocking operations
- Best suited for request-response workflows, where each request waits for a result before proceeding

2. Asynchronous Client
- Uses non-blocking operations.
- Ideal for reactive applications that handle high concurrency and streaming responses

For our GitHub example, we use the `SYNC` client since it follows a structured request-response pattern.

**How to switch between client modes?**

Update `application.yml` to select either SYNC (default) or ASYNC:

```yaml
spring:
  ai:
    mcp:
      client:
        type: ASYNC  # Change to SYNC for blocking operations
```

## Conclusion

We've explored how MCP and Spring AI MCP unlock new possibilities for AI-driven workflows, seamlessly integrating LLMs with external systems. The GitHub Lens example demonstrated how MCP simplifies complex interactions, making AI-powered automation effortless.

But this is just the beginning!

To explore this project in more detail, visit the [GitHub repository](https://github.com/vudayani/spring-mcp-examples/tree/main/github-lens)

If you're excited to explore another real-world use case? Take a look at [Lunch Planner](https://github.com/vudayani/spring-mcp-examples/tree/main/lunch-planner), an MCP-powered AI assistant that helps coordinate lunch plans based on team preferences

MCP is transforming AI-driven workflows, enabling smarter automation and seamless integrations. Give it a try and start building AI-powered applications!