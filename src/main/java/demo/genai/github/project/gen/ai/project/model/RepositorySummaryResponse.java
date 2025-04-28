package demo.genai.github.project.gen.ai.project.model;

import lombok.Data;

import java.util.List;
@Data
public class RepositorySummaryResponse {
    private List<FileSummary> content;
}
