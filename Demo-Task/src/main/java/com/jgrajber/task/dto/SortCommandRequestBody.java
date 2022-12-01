package com.jgrajber.task.dto;

import java.util.List;

public record SortCommandRequestBody(List<Integer> numbers, String order) {
}
