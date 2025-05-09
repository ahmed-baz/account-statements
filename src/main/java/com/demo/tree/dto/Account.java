package com.demo.tree.dto;

import lombok.Builder;

@Builder
public record Account(
        Long id,
        String accountType,
        String accountNumber
) {

}
