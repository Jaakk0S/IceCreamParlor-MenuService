package com.catsoft.demo.icecreamparlor.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"dev.mode=true"})
public class CORSDevModeIntegrationTests extends AbstractIntegrationTests {

    @Test
    void inDevMode_shouldReturn_AllowOrigin_WithRandomOrigin() {

        final String validOrigin = "http://blablabla";

        HttpHeaders headers = new HttpHeaders();
        headers.setOrigin(validOrigin);
        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = this.restTemplate.exchange(super.getUrl("healthcheck", -1), HttpMethod.GET, request, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getHeaders().get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)).contains(validOrigin);
    }

}