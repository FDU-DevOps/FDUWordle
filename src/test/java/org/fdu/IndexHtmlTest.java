package org.fdu;

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
    void testIndexPageLoads(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk();
    }

    @Test
    void testIndexPageContainsTitle(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("FDUWordle");
    }

    @Test
    void testIndexPageContainsGuessInput(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("submit-guess");
    }

    @Test
    void testIndexPageContainsSubmitButton(@Autowired RestTestClient restClient)
    {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().contains("sendUserGuess");
    }
}