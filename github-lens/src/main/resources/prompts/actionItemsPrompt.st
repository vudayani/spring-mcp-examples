Provide actionable reminders and notifications for the GitHub repository {repoName} owned by {repoOwner}. Focus on tasks requiring immediate attention:

1. Pull Requests:
   - Identify pull requests that have been awaiting review for more than 2 days
   - Include the PR title, author, and reviewers who need to take action

2. Unresolved Comments:
   - Extract pull requests with unresolved comments
   - Include the PR title, author, and assignees responsible for addressing the comments

3. Prioritized Issues:
   - List issues labeled as 'release-blocker' or 'high-priority'
   - Include issue titles, authors, labels, and any relevant milestones
   - Suggest next steps for addressing these issues

4. Format for Slack:
   - Structure the message into actionable sections, highlighting tasks for specific team members
   - Use bullet points, mentions (e.g., @username), or attachments for readability

The reminders should be clear, concise, and actionable to help the team address critical tasks efficiently. Post the final summary to the '#dev-updates' Slack channel.
