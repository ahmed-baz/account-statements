package com.demo.tree.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record LoginSession(
        String userName,
        Date loginDate
) {
}
