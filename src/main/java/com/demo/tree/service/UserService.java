package com.demo.tree.service;

import com.demo.tree.dto.LoginSession;

public interface UserService {

    LoginSession setLoginSession(String userName);

    void clearSession(String userName);

    void validateLoginSession(String userName);
}
