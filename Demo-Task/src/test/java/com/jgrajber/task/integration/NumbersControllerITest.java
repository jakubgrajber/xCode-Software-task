package com.jgrajber.task.integration;

import com.jgrajber.task.dto.SortCommandRequestBody;
import com.jgrajber.task.dto.SortCommandResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.jgrajber.task.utils.SortCommandOrder.ASC;
import static com.jgrajber.task.utils.SortCommandOrder.DESC;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NumbersControllerITest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    String URI_UNDER_TEST;
    static final List<Integer> ASC_SORTED_LIST = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    static final List<Integer> DESC_SORTED_LIST = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    static final List<Integer> LIST_OF_NUMBERS = List.of(3, 6, 0, 1, 2, 7, 4, 5, 9, 8);

    @BeforeEach
    void setUp() {
        URI_UNDER_TEST = "http://localhost:%d/numbers/sort-command".formatted(port);
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was also empty.")
    void givenEmptyList_whenSortCommand_thenReturnEmptyListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(new ArrayList<>(), ASC.toString());

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(new ArrayList<>(), response.getBody().numbers());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was null.")
    void givenNull_whenSortCommand_thenReturnEmptyListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(null, ASC.toString());

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(new ArrayList<>(), response.getBody().numbers());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an empty list when given list was null.")
    void givenSingleElementList_whenSortCommand_thenReturnTheSameListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(List.of(1), ASC.toString());

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(List.of(1), response.getBody().numbers());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an sorted list in the ASC order")
    void givenListAndASCOrderCode_whenSortCommand_thenReturnSortedListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(LIST_OF_NUMBERS, ASC.toString());

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(ASC_SORTED_LIST, response.getBody().numbers());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns an sorted list in the DESC order")
    void givenListAndDESCOrderCode_whenSortCommand_thenReturnSortedListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(LIST_OF_NUMBERS, DESC.toString());

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(DESC_SORTED_LIST, response.getBody().numbers());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns BAD_REQUEST when given order code is incorrect")
    void givenListAndIncorrectOrderCode_wheSortCommand_thenReturnBadRequest() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(LIST_OF_NUMBERS, "incorrect-order-code");

        //when
        ResponseEntity<String> response = restTemplate.postForEntity(URI_UNDER_TEST, requestBody, String.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("POST numbers/sort-command - returns a sorted list when given order code is lower case")
    void givenListAndLowerCaseOrderCode_wheSortCommand_thenReturnSortedListAndOK() throws Exception {

        //given
        SortCommandRequestBody requestBody = new SortCommandRequestBody(LIST_OF_NUMBERS, "desc");

        //when
        ResponseEntity<SortCommandResponse> response =
                restTemplate.postForEntity(URI_UNDER_TEST, requestBody, SortCommandResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(DESC_SORTED_LIST, response.getBody().numbers());
    }
}
