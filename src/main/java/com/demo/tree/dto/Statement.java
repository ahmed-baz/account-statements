package com.demo.tree.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record Statement(
        Long id,
        Account account,
        @JsonFormat(pattern = "YYYY-MM-dd")
        Date date,
        Double amount
) {
}
