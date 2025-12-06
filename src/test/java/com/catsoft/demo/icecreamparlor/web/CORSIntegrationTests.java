package com.catsoft.demo.icecreamparlor.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CORSIntegrationTests extends AbstractIntegrationTests {

    @Test
    void shouldReturn_AllowOrigin_WithWhitelistedOrigin() {

        final String validOrigin = "http://nginx";

        HttpHeaders headers = new HttpHeaders();
        headers.setOrigin(validOrigin);
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = this.restTemplate.exchange(super.getUrl("healthcheck", -1), HttpMethod.GET, request, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getHeaders().get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).contains(validOrigin);
    }

    @Test
    void shouldReturn_FORBIDDEN_WithInvalidOrigin() {

        final String invalidOrigin = "http://blabla";

        HttpHeaders headers = new HttpHeaders();
        headers.setOrigin(invalidOrigin);
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(super.getUrl("healthcheck", -1), HttpMethod.GET, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(403));
        assertThat(response.getBody()).isEqualTo("Invalid CORS request");
    }

}