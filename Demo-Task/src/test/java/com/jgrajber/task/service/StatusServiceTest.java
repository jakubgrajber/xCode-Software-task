package com.jgrajber.task.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {

    StatusService statusService = new StatusService();

    static final String STATUS_MESSAGE = "pong";

    @Test
    void shouldReturnMessage() {

        // when
        String response = statusService.getStatusMessage();

        //then
        Assertions.assertEquals(STATUS_MESSAGE, response);
    }
}