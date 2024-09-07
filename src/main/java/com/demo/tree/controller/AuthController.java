package com.demo.tree.controller;


import com.demo.tree.dto.AppResponse;
import com.demo.tree.security.service.AuthService;
import com.demo.tree.security.vo.LoginRequest;
import com.demo.tree.security.vo.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AppResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return AppResponse.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public AppResponse<Void> logout() {
        authService.logout();
        return AppResponse.ok(null);
    }

}
