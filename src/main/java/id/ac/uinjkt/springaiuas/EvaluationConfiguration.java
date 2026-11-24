package id.ac.uinjkt.springaiuas;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvaluationConfiguration {

    @Bean
    RelevancyEvaluator relevancyEvaluator(ChatClient.Builder chatClientBuilder) {
        return new RelevancyEvaluator(chatClientBuilder);
    }
}
