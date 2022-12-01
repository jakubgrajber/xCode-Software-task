package com.jgrajber.task.service;

import com.jgrajber.task.utils.SortCommandOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jgrajber.task.utils.SortCommandOrder.*;
import static org.junit.jupiter.api.Assertions.*;

class NumbersServiceImplTest {

    NumbersService numbersService = new NumbersServiceImpl();

    @Test
    void shouldSortAscending() {

        // given
        List<Integer> listUnderTest = new ArrayList<>(List.of(2, 5, 3, 7, 3));

        // when
        List<Integer> sortedList = numbersService.sort(listUnderTest, ASC);

        // then
        Assertions.assertEquals(List.of(2, 3, 3, 5, 7), sortedList);

    }

    @Test
    void shouldSortDescending() {

        // given
        List<Integer> listUnderTest = new ArrayList<>(List.of(2, 5, 3, 7, 3));

        // when
        List<Integer> sortedList = numbersService.sort(listUnderTest, DESC);

        // then
        Assertions.assertEquals(List.of(7, 5, 3, 3, 2), sortedList);

    }
}