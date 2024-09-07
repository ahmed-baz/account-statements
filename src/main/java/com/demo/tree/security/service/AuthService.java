package com.demo.tree.security.service;


import com.demo.tree.security.vo.LoginRequest;
import com.demo.tree.security.vo.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    LoginResponse login(LoginRequest requestVO);

    void logout();
}
