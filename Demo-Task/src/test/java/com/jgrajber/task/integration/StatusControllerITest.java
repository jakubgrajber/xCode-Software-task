package com.jgrajber.task.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusControllerITest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    Integer port;

    static final String STATUS_MESSAGE = "pong";

    @Test
    void shouldReturnStatusMessageAndOK() {

        String baseUrl = "http://localhost:" + port + "/status/ping";

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getBody());
    }
}
