package com.jgrajber.task.service;

import com.jgrajber.task.utils.SortCommandOrder;

import java.util.List;

public interface NumbersService {

    List<Integer> sort(List<Integer> list, SortCommandOrder order);
}
