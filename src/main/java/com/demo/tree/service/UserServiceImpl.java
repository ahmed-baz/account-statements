package com.demo.tree.service;

import com.demo.tree.dto.LoginSession;
import com.demo.tree.exceptions.LoginTwiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CacheManager cacheManager;

    @Override
    @Cacheable(value = "userLogin", key = "#userName")
    public LoginSession setLoginSession(String userName) {
        log.info("setLoginSession");
        return LoginSession
                .builder()
                .userName(userName)
                .loginDate(new Date())
                .build();
    }

    @Override
    @CacheEvict(value = "userLogin", key = "#userName")
    public void clearSession(String userName) {
        log.info("clearSession with {}", userName);
    }

    @Override
    public void validateLoginSession(String userName) {
        Cache cache = cacheManager.getCache("userLogin");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(userName);
            LoginSession loginSession = valueWrapper != null ? (LoginSession) valueWrapper.get() : null;
            if (loginSession != null) {
                log.info("user name [{}]", loginSession.userName());
                log.info("logout date [{}]", loginSession.loginDate());
                throw new LoginTwiceException("Login twice is not allowed, please logout first");
            }
        }
    }
}
