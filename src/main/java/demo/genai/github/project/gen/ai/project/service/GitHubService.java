package demo.genai.github.project.gen.ai.project.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class GitHubService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${github.token}")
    private String githubToken;

    private static final String GITHUB_API_URL = "https://api.github.com/repos/{owner}/{repo}/contents/{path}";

    public List<String> getAllFiles(String owner, String repo) {
        List<String> allFiles = new ArrayList<>();
        fetchFilesRecursive(owner, repo, "", allFiles);
        Map<String, List<String>> folderToJavaFiles = new HashMap<>();
        fetchFilesRecursive(owner, repo, "", allFiles);
        return allFiles;
        }


    private void fetchFilesRecursive(String owner, String repo, String path, List<String> allFiles) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);

        String finalPath = path != null ? path : "";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(GITHUB_API_URL);
        URI uri = uriBuilder
                .buildAndExpand(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "path", finalPath
                ))
                .toUri();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, List.class);

        List<Map<String, Object>> files = response.getBody();
        if (files != null) {
            for (Map<String, Object> file : files) {
                String type = (String) file.get("type");
                String filePath = (String) file.get("path");
                if ("file".equals(type) && filePath.endsWith(".java")) {
                    allFiles.add(filePath);
                } else if ("dir".equals(type)) {
                    fetchFilesRecursive(owner, repo, filePath, allFiles);
                }
            }
        }
    }

}
