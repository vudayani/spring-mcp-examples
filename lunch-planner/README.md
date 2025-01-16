# Lunch Planner & Announcement using Spring MCP(Model Context Protocol)


The Lunch Planner & Announcement project is a small demo application showcasing the use of Model Context Protocol (MCP) to integrate AI-driven workflows. The project uses publicly available MCP servers for [Google Maps](https://github.com/modelcontextprotocol/servers/tree/main/src/google-maps) and [Slack](https://github.com/modelcontextprotocol/servers/tree/main/src/slack), enabling seamless interaction.

In this application, users can:

1. Find a nearby lunch spot that meets specific criteria (e.g., vegetarian options, distance limits) using the Google Maps MCP server

2. Announce the lunch plan (time and location) in a Slack channel using the Slack MCP server

By leveraging these MCP servers, the application demonstrates how an AI assistant can seamlessly handle location-based queries and team communication—all through natural language interactions.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- npx package manager
- OpenAI API key
- Google Maps API Key
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
   cd lunch-planner
   ```

3. Set up your API keys:
   ```bash
   export OPENAI_API_KEY='your-openai-api-key'
   export GOOGLE_MAPS_API_KEY='your-google-maps-api-key'
   export SLACK_BOT_TOKEN='your-slack-api-key'
   ```
   - Fetch the Google Maps API key by following the instructions [here](https://developers.google.com/maps/documentation/javascript/get-api-key#create-api-keys)
   - Setup and fetch slack api token by following the instructions [here](https://github.com/modelcontextprotocol/servers/blob/main/src/slack/README.md#setup)
   

5. Build the application:
   ```bash
   ./mvnw clean install
   ```

## Running the Application

Run the application using Maven:
```bash
./mvnw spring-boot:run
```

Once running, the application will:

1. Parse user prompt

2. Query Google Maps to find suitable lunch spots

3. Post the finalized plan to a Slack channel


## Dependencies

1. Spring Boot 3.4.1

2. Spring AI 1.0.0-M5

3. Spring AI MCP Spring 0.4.1

4. OpenAI Spring Boot Starter


## How It Works

When the application is started:

1. MCP Client Initialization:

- The Spring MCP clients are created to establish connections to the MCP servers (Google Maps and Slack)

- The MCP clients query the servers to discover the available tools

2. Function Callbacks Registration:

- The discovered tools are registered as Spring AI function callback

- These callbacks allow the tools to be seamlessly invoked during conversations

3. ChatClient Setup:

- The ChatClient is initialized and registered with the function callbacks

- This setup ensures the ChatClient can determine and invoke the appropriate tools based on user prompts.

The Flow:

1. User Prompt: The user provides a natural language prompt specifying location criteria and announcement details

2. ChatClient Interaction: The ChatClient interacts with the LLM (OpenAI in this case) to handle the entire flow. It analyzes the prompt and identifies which tools to invoke

3. Google Maps MCP Server: The ChatClient calls the Google Maps tool to fetch restaurant data based on the user’s criteria (e.g., vegetarian options, distance limits). The server returns a list of recommendations with details like names, ratings, and addresses

4. Slack MCP Server: After selecting a restaurant, the ChatClient calls the Slack tool to post an announcement in the specified channel. A friendly message is sent, detailing the time and location of the lunch plan

This architectural design illustrates how AI-driven workflows can simplify complex tasks, such as querying maps and sending notifications, making the process intuitive for end users.




