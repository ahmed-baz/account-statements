package com.demo.tree.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record Statement(
        Long id,
        Account account,
        Long accountId,
        @JsonFormat(pattern = "YYYY-MM-dd")
        Date date,
        Double amount
) {
}
