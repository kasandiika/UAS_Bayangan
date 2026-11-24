package id.ac.uinjkt.springaiuas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@EnableWireMock(
        @ConfigureWireMock(baseUrlProperties = "openai.base.url"))
@SpringBootTest(properties = {
        "spring.ai.openai.base-url=${openai.base.url}",
        "spring.ai.openai.api-key=test-api-key"
})
class SpringAiBoardGameServiceWireMockTests {

    @Value("classpath:/test-openai-response.json")
    Resource responseResource;

    @Autowired
    ChatClient.Builder chatClientBuilder;

    @BeforeEach
    void setup() throws IOException {
        var cannedResponse = responseResource.getContentAsString(StandardCharsets.UTF_8);
        var responseNode = new ObjectMapper().readTree(cannedResponse);
        WireMock.stubFor(WireMock.post("/v1/chat/completions")
                .willReturn(ResponseDefinitionBuilder.okForJson(responseNode)));
    }

    @Test
    void returnsAnswerFromProviderResponse() {
        var service = new SpringAiBoardGameService(chatClientBuilder);

        var answer = service.askQuestion(new Question("What is the capital of France?"));

        assertThat(answer).isNotNull();
        assertThat(answer.answer()).isEqualTo("Paris");
    }
}
