package com.demo.tree.security.vo;

import com.demo.tree.enums.RoleTypeEnum;
import lombok.Builder;

import java.util.List;

@Builder
public record LoginResponse(
        String userName,
        List<RoleTypeEnum> roles,
        String accessToken
) {
}


