package demo.genai.github.project.gen.ai.project.controller;

import demo.genai.github.project.gen.ai.project.model.FileSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import demo.genai.github.project.gen.ai.project.service.GenAIServie;
import demo.genai.github.project.gen.ai.project.service.GitHubService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/github")
public class GitHubSummaryController {

    @Autowired
    private GenAIServie genAIServie;
    @Autowired
    private GitHubService gitHubService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @GetMapping("/summarize")
    public Map<String, List<FileSummary>> summerizeProject(@RequestParam String owner, @RequestParam String repo){
        List<String> javaFilesFromGit = gitHubService.getAllFiles(owner, repo);
        List<CompletableFuture<FileSummary>> futures = javaFilesFromGit.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> {
                    String summary = genAIServie.summarizeCode(file);
                    return new FileSummary(file, summary);
                }, executorService))
                .collect(Collectors.toList());

        List<FileSummary> summaries = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return Map.of("content", summaries);
    }



}
