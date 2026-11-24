package id.ac.uinjkt.springaiuas;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Profile("self-evaluating")
public class SelfEvaluatingBoardGameService implements BoardGameService {

    private final ChatClient chatClient;
    private final RelevancyEvaluator evaluator;

    public SelfEvaluatingBoardGameService(
            ChatClient.Builder chatClientBuilder,
            RelevancyEvaluator evaluator) {
        this.chatClient = chatClientBuilder.build();
        this.evaluator = evaluator;
    }

    @Override
    @Retryable(retryFor = AnswerNotRelevantException.class, maxAttempts = 3)
    public Answer askQuestion(Question question) {
        var answerText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();

        var evaluationRequest = new EvaluationRequest(question.question(), answerText);
        var evaluationResponse = evaluator.evaluate(evaluationRequest);
        if (!evaluationResponse.isPass()) {
            throw new AnswerNotRelevantException(question.question(), answerText);
        }

        return new Answer(answerText);
    }

    @Recover
    public Answer recover(AnswerNotRelevantException exception) {
        return new Answer("Maaf, saya belum dapat memberikan jawaban yang relevan. Silakan coba pertanyaan lain.");
    }
}
