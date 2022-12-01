package com.jgrajber.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgrajber.task.dto.SortCommandRequestBody;
import com.jgrajber.task.dto.SortCommandResponse;
import com.jgrajber.task.service.NumbersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.jgrajber.task.utils.SortCommandOrder.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumbersController.class)
class NumbersControllerTest {

    @MockBean
    NumbersService numbersService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    static final String URI_UNDER_TEST = "/numbers/sort-command";
    static final List<Integer> ASC_SORTED_LIST = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    static final List<Integer> DESC_SORTED_LIST = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    static final List<Integer> LIST_OF_NUMBERS = List.of(3, 6, 0, 1, 2, 7, 4, 5, 9, 8);

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was also empty.")
    void givenEmptyList_whenSortCommand_thenReturnEmptyListAndOK() throws Exception {

        this.mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "numbers": [],
                                    "order": "ASC"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers", empty()))
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was null.")
    void givenNull_whenSortCommand_thenReturnEmptyListAndOK() throws Exception {

        this.mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "numbers": null,
                                    "order": "ASC"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers", empty()))
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was null.")
    void givenSingleElementList_whenSortCommand_thenReturnTheSameListAndOK() throws Exception {

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "numbers": [1],
                                    "order": "ASC"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers.size()", is(1)))
                .andExpect(jsonPath("$.numbers[0]", is(1)))
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an sorted list in the ASC order")
    void givenListAndASCOrderCode_whenSortCommand_thenReturnSortedListAndOK() throws Exception {

        when(numbersService.sort(LIST_OF_NUMBERS, ASC)).thenReturn(ASC_SORTED_LIST);

        String requestBody = objectMapper.writeValueAsString(new SortCommandRequestBody(LIST_OF_NUMBERS, ASC.toString()));
        String response = objectMapper.writeValueAsString(new SortCommandResponse(ASC_SORTED_LIST));

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(response, true))
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an sorted list in the DESC order")
    void givenListAndDESCOrderCode_whenSortCommand_thenReturnSortedListAndOK() throws Exception {

        when(numbersService.sort(LIST_OF_NUMBERS, DESC)).thenReturn(DESC_SORTED_LIST);

        String requestBody = objectMapper.writeValueAsString(new SortCommandRequestBody(LIST_OF_NUMBERS, DESC.toString()));
        String response = objectMapper.writeValueAsString(new SortCommandResponse(DESC_SORTED_LIST));

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(response, true))
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns BAD_REQUEST when given order code is incorrect")
    void givenListAndIncorrectOrderCode_wheSortCommand_thenReturnBadRequest() throws Exception {

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "numbers": [1,2,3],
                                    "order": "incorrect-order-code"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns a sorted list when given order code is lower case")
    void givenListAndLowerCaseOrderCode_wheSortCommand_thenReturnSortedListAndOK() throws Exception {

        String expectedResponse = objectMapper.writeValueAsString(new SortCommandResponse(List.of(3, 2, 1)));
        BDDMockito.when(numbersService.sort(List.of(1, 2, 3), DESC)).thenReturn(List.of(3, 2, 1));

        mockMvc.
                perform(post(URI_UNDER_TEST).
                        contentType(APPLICATION_JSON)
                        .content("""
                                {
                                    "numbers": [1,2,3],
                                    "order": "desc"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true))
                .andDo(print());
    }
}