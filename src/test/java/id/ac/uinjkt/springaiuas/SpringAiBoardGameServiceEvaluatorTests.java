package id.ac.uinjkt.springaiuas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("live-ai")
@EnabledIfEnvironmentVariable(named = "SPRING_AI_OPENAI_API_KEY", matches = ".+")
@SpringBootTest
class SpringAiBoardGameServiceEvaluatorTests {

    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private RelevancyEvaluator relevancyEvaluator;
    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    void setup() {
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    void generatedAnswerIsRelevantToQuestion() {
        var userText = "Why is the sky blue?";
        var answer = boardGameService.askQuestion(new Question(userText));
        var response = relevancyEvaluator.evaluate(
                new EvaluationRequest(userText, answer.answer()));

        assertThat(response.isPass())
                .withFailMessage("The generated answer was not judged relevant: %s", answer.answer())
                .isTrue();
    }

    @Test
    void generatedAnswerIsFactuallyCorrect() {
        var userText = "Why is the sky blue?";
        var answer = boardGameService.askQuestion(new Question(userText));
        EvaluationResponse response = factCheckingEvaluator.evaluate(
                new EvaluationRequest(userText, answer.answer()));

        assertThat(response.isPass())
                .withFailMessage("The generated answer was not judged factually correct: %s", answer.answer())
                .isTrue();
    }
}
