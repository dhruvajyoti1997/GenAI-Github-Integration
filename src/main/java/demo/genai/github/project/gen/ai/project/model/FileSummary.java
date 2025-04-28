package demo.genai.github.project.gen.ai.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSummary {
    private String file;
    private String summary;


}
