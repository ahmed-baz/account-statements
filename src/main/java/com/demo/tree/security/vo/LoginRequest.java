package com.demo.tree.security.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotNull(message = "username is required")
        @NotEmpty(message = "username is required")
        String userName,
        @NotNull(message = "password is required")
        String password
) {
}


