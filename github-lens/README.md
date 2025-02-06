# GitHub Lens using Spring MCP(Model Context Protocol)

GitHub Lens: Your AI-powered assistant for staying on top of GitHub activities.

GitHub Lens provides real-time insights, daily summaries, and automated reminders, delivered seamlessly to your team on Slack. Built using Spring MCP (Model Context Protocol), it leverages MCP servers for [GitHub](https://github.com/modelcontextprotocol/servers/tree/main/src/github) and [Slack](https://github.com/modelcontextprotocol/servers/tree/main/src/slack) to automate routine tasks, enabling your team to stay focused on development.

## Features

## How It Works

GitHub Lens is powered by Spring MCP, enabling seamless interaction between GitHub and Slack MCP servers. It uses an LLM (Large Language Model) to:

1. Understand prompts and generate meaningful insights

2. Automatically call tools from GitHub and Slack MCP servers

3. Deliver summaries, reminders, and updates directly to Slack

## Architecture Overview

[GitHub MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/github): Fetches repository data such as PRs, issues, and commits.

[Slack MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/slack): Posts summaries and notifications to Slack channels.


## Prerequisites

- Java 17 or higher
- Maven 3.6+
- npx package manager
- OpenAI API key
- Github API Key
- Slack API Key

## Setup

1. Install npx (Node Package eXecute):
   First, make sure to install [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
   and then run:
   ```bash
   npm install -g npx
   ```

2. Using a Forked GitHub MCP Server for Full Functionality:

The public [GitHub MCP Server]((https://github.com/modelcontextprotocol/servers/tree/main/src/github)) currently doesn't expose some tools for pull request operations. To ensure full functionality in the GitHub-Lens project, follow these steps to fork, build, and use the customized version of the GitHub MCP server.

2.1 Clone the forked repository which exposes the additional tools for pull request operations:

```bash
git clone https://github.com/vudayani/servers.git
```

2.2 Build the GitHub MCP Server
Ensure Node.js and npm are installed before proceeding.

```bash
cd src/github
npm install
```

2.3 Configure Spring MCP parameters to use the local github mcp server

Modify the GitHub MCP client configuration in your Spring Boot application to point to the local server.

```java
	@Bean(destroyMethod = "close")
	McpSyncClient githubMcpClient() {
		// based on https://github.com/modelcontextprotocol/servers/tree/main/src/github
		var githubMcpClient = ServerParameters.builder("npx")
            .args("-y", "/Users/vudayani/Desktop/Spring-ai/mcp-servers-clone/servers/src/github")
				.addEnvVar("GITHUB_PERSONAL_ACCESS_TOKEN", System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN"))
            .build();
		var mcpClient = McpClient.sync(new StdioClientTransport(githubMcpClient))
				.requestTimeout(Duration.ofSeconds(10))
            .build();
		var init = mcpClient.initialize();
		return mcpClient;
	}
```
**Note:** Update the local server path based on your actual directory.

3. Clone the repository:
   ```bash
   git clone https://github.com/vudayani/spring-mcp-examples.git
   cd github-lens
   ```

4. Set up your API keys:
   ```bash
   export OPENAI_API_KEY='your-openai-api-key'
   export GITHUB_PERSONAL_ACCESS_TOKEN='your-github-personal-access-token'
   export SLACK_BOT_TOKEN='your-slack-api-key'
   export SLACK_TEAM_ID='your-slack-team-id`
   ```
For more information on fetching the tokens, please refer the MCP server documentation [here](https://github.com/modelcontextprotocol/servers/tree/main/src)

5. Build the application:
   ```bash
   ./mvnw clean install
   ```
   
6. Run the application using Maven:
	```bash
	./mvnw spring-boot:run
	```
