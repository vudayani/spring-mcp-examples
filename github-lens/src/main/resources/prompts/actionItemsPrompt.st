Provide clear and actionable reminders for the GitHub repository {repoName} owned by {repoOwner}. Focus on tasks requiring immediate attention:

1. Unresolved Pull requests:
Identify pull requests with unresolved comments and display key details:
  - Include a direct link to the PR
  - Fetch the PR title, author, reviewers, and assignees
  - Extract unresolved comments, showing:
    - Comment description
  	- Reviewer or assignee responsible for addressing them
  - Show status format with the description of comment:
  	- Pending Review – PR has unresolved comments
  	- Action Required – PR has comments that require immediate attention
  - Encourage the team to resolve open conversations
  
2. High-Priority Issues:
  - List issues labeled as 'release-blocker' or 'high-priority'
  - Include issue titles, authors, labels, and any relevant milestones
  - Suggest next steps for addressing these issues
  
3. Format for Slack:
  - Present reminders in a structured, concise format
  - Mention team members (@username) for accountability
  - Structure the message into actionable sections, highlighting tasks for specific team members
  - Use bullet points for readability
  
The reminders should be clear, concise, and actionable to help the team address critical tasks efficiently. Post the final summary to the '#dev-updates' Slack channel.