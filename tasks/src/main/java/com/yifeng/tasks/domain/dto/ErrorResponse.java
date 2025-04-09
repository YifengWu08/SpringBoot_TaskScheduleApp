package com.yifeng.tasks.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
