package id.ac.uinjkt.springaiuas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AskController.class)
class AskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardGameService boardGameService;

    @Test
    void acceptsQuestionAndReturnsJsonAnswer() throws Exception {
        given(boardGameService.askQuestion(new Question("What is Spring AI?")))
                .willReturn(new Answer("A Spring framework for AI."));

        mockMvc.perform(post("/ask")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"question":"What is Spring AI?"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("A Spring framework for AI."));
    }
}
