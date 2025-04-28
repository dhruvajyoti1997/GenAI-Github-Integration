package demo.genai.github.project.gen.ai.project.service;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class GenAIServie {
    private final ChatModel chatModel;

    public GenAIServie(ChatModel chatClient) {
        this.chatModel = chatClient;
    }

    public String summarizeCode(String javaCode) {

            String prompt = "explain in only 3 points \n\n" + javaCode;
           return   chatModel.call(prompt);

    }
}
