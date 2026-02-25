package org.fdu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.assertj.RestTestClientResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestTestClient

class WordleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test //https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.with-mock-environment
    void testWithMockMvcTester(@Autowired MockMvcTester mvc) {
        assertThat(mvc.get().uri("/"))
                .hasStatusOk()
                .hasBodyTextEqualTo(""); //Currently we have nothing in the bodytext returned on get
    }

    //Note, we should probably discuss if we are using RestTest logic (full implementation) or MockMvcTester (I recommend the rest one, this only focuses on web layer stuff)

    @Test
    void testWithRestTestClientAssertJ(@Autowired RestTestClient restClient) {
        RestTestClient.ResponseSpec spec = restClient.get().uri("/").exchange();
        RestTestClientResponse response = RestTestClientResponse.from(spec);
        assertThat(response).hasStatusOk().bodyText().isEqualTo(""); //currently we have nothing in the bodytext returned on get
    }

}