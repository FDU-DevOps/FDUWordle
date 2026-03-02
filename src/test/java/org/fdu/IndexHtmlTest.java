package org.fdu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestTestClient
public class IndexHtmlTest
{
    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Testing index (main) page loads properly.")
    void testIndexPageLoads(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk();
    }

    @Test
    @DisplayName("Testing that the index (main) page loads with the title FDUWordle")
    void testIndexPageContainsTitle(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("FDUWordle");
    }

    @Test
    @DisplayName("Testing that the index (main) page contains guess input for the user.")
    void testIndexPageContainsGuessInput(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("submit-guess");
    }

    @Test
    @DisplayName("Testing that the index (main) page has the submit button for the user to submit a guess.")
    void testIndexPageContainsSubmitButton(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("sendUserGuess");
    }
}