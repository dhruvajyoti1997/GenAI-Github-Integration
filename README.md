GitHub Repository Summarizer (Java + GenAI)

This Spring Boot API integrates with GitHub to analyze Java repositories, extract .java files from all directories, and generate AI-powered summaries using the DeepSeek R1 model via Ollama.

Key Features

1️⃣ 
Fetches GitHub Repository Details
Extracts metadata like repository owner, repo name, and directory structure.
Uses GitHub’s REST API to fetch repository contents with proper authentication (GitHub Personal Access Token).

2️⃣ 
Recursively Scans All Directories for .java Files
Traverses all folders (e.g., src/main/java, src/test/java) to find Java source files.
Filters only .java files, ignoring non-Java files (like .xml, .properties).
Handles nested directory structures (e.g., com/example/demo/controllers/, utils/).
				
3️⃣ 
Generates AI Summaries for Each Java Class Using DeepSeek R1
Reads each .java file and extracts its code.
Sends the code to Ollama (DeepSeek R1) for AI-powered summarization.
Returns concise summaries explaining

 Prerequisites: 
1. Java 17+
2. Maven
3. Ollama installed & running (for local AI inference)
4. GitHub Personal Access Token (for API access)

GET Request:
GET /api/github/summarize?owner={owner}&repo={repo}
			
				
				
