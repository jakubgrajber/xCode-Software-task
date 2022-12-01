package com.jgrajber.task.controller;

import com.jgrajber.task.service.StatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StatusController.class)
class StatusControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StatusService statusService;

    @Test
    @DisplayName("GET status/ping - receive status message")
    void whenGetStatus_ThenReturnStringWithMessage() throws Exception {

        // given
        String message = "pong";
        when(statusService.getStatusMessage()).thenReturn(message);


        // when - then
        this.mockMvc.perform(get("/status/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(message)));
    }
}