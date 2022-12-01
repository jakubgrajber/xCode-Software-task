package com.jgrajber.task.service;

import com.jgrajber.task.utils.SortCommandOrder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.jgrajber.task.utils.SortCommandOrder.*;

@Service
public class NumbersServiceImpl implements NumbersService {

    @Override
    public List<Integer> sort(List<Integer> list, SortCommandOrder order) {

        if (order.equals(ASC)) {
            list.sort(Integer::compareTo);
        } else {
            list.sort(Comparator.reverseOrder());
        }
        return list;
    }
}
