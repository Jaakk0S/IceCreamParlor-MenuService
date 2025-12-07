package com.catsoft.demo.icecreamparlor.web;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTests {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getUrl(String methodName, int id) {
        var url = "http://localhost:%d/menu/v1/%s";
        if (id >= 0)
            url += "/" + id;
        return String.format(url, this.port, methodName);
    }

    protected <T> T exchangeAndExpectStatus(String uri, HttpMethod method, HttpEntity<?> requestEntity, int status, Class<T> klass) {
        try {
            ResponseEntity<T> result = this.restTemplate.exchange(uri, method, requestEntity, klass);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(status));
            return result.getBody();
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(status));
        } catch (RestClientException e) {
            IO.println(e.getClass());
            IO.println(e.getMessage());
            IO.println(e.getRootCause());
            assertThat(false).isTrue();
        }
        return null;
    }
}