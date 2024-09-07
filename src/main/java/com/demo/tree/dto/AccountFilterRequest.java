package com.demo.tree.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.Date;

@Builder
public record AccountFilterRequest(
        @NotNull(message = "account ID is required")
        Long accountId,
        Date dateFrom,
        Date dateTo,
        @PositiveOrZero(message = "invalid amount")
        Double amountFrom,
        @PositiveOrZero(message = "invalid amount")
        Double amountTo
) {
}
