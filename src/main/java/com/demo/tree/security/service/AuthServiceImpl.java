package com.demo.tree.security.service;


import com.demo.tree.exceptions.InvalidCredentialsException;
import com.demo.tree.exceptions.UserNotFoundException;
import com.demo.tree.security.util.JwtTokenUtil;
import com.demo.tree.security.vo.AppUserDetails;
import com.demo.tree.security.vo.LoginRequest;
import com.demo.tree.security.vo.LoginResponse;
import com.demo.tree.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public LoginResponse login(LoginRequest requestVO) {
        Authentication authentication = authenticateUser(requestVO);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LoginResponse loginResponse = jwtTokenUtil.prepareLoginResponse(userDetails);
        userService.validateLoginSession(requestVO.userName());
        userService.setLoginSession(requestVO.userName());
        return loginResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUserDetails userDetails = null;
        try {
            userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            log.error(ex);
            throw ex;
        }
        return userDetails;
    }

    private Authentication authenticateUser(LoginRequest requestVO) {
        try {
            return authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(requestVO.userName(), requestVO.password()));
        } catch (InternalAuthenticationServiceException
                 | BadCredentialsException
                 | UserNotFoundException ex) {
            log.error(ex);
            throw new InvalidCredentialsException("invalid username or password");
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }

    @Override
    public void logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(null);
        userService.clearSession(userDetails.getUsername());
    }

}

