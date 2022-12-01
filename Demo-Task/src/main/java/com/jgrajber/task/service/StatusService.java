package com.jgrajber.task.service;

import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public String getStatusMessage() {
        return "pong";
    }
}
