package com.catsoft.demo.icecreamparlor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTests {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getUrl(String methodName, int id) {
        var url = "http://localhost:%d/admin/v1/%s";
        if (id >= 0)
            url += "/" + id;
        return String.format(url, this.port, methodName);
    }
}