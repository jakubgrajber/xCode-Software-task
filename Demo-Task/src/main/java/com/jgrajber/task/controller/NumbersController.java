package com.jgrajber.task.controller;

import com.jgrajber.task.dto.SortCommandRequestBody;
import com.jgrajber.task.dto.SortCommandResponse;
import com.jgrajber.task.service.NumbersService;
import com.jgrajber.task.utils.SortCommandOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("numbers")
@CrossOrigin("http://localhost:4200")
public class NumbersController {

    private NumbersService numbersService;

    public NumbersController(NumbersService numbersService) {
        this.numbersService = numbersService;
    }

    @PostMapping("sort-command")
    public ResponseEntity sortCommand(@RequestBody SortCommandRequestBody requestBody) {

        List<Integer> numbers = requestBody.numbers();
        String order = requestBody.order();

        if (numbers == null || numbers.size() == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new SortCommandResponse(new ArrayList<>()));
        }

        if (numbers.size() == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new SortCommandResponse(numbers));
        }

        SortCommandOrder orderEnum;

        try {
            orderEnum = SortCommandOrder.valueOf(order.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Incorrect order code. \nUse ASC for ascending and DESC for descending order.");
        }

        List<Integer> result = numbersService.sort(numbers, orderEnum);
        return new ResponseEntity<>(new SortCommandResponse(result), HttpStatus.OK);
    }
}
