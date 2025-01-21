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

2. Clone the repository:
   ```bash
   git clone https://github.com/vudayani/spring-mcp-examples.git
   cd github-lens
   ```

3. Set up your API keys:
   ```bash
   export OPENAI_API_KEY='your-openai-api-key'
   export GITHUB_PERSONAL_ACCESS_TOKEN='your-github-personal-access-token'
   export SLACK_BOT_TOKEN='your-slack-api-key'
   export SLACK_TEAM_ID='your-slack-team-id`
   ```
For more information on fetching the tokens, please refer the MCP server documentation [here](https://github.com/modelcontextprotocol/servers/tree/main/src)

4. Build the application:
   ```bash
   ./mvnw clean install
   ```
   
5. Run the application using Maven:
	```bash
	./mvnw spring-boot:run
	```


## Dependencies

1. Spring Boot 3.4.1

2. Spring AI 1.0.0-M5

3. Spring AI MCP Spring 0.4.1

4. OpenAI Spring Boot Starter




