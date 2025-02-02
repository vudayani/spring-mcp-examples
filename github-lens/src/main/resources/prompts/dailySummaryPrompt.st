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
   
The summary should be concise, clear, and actionable to help the development team quickly understand the repository's status and priorities
