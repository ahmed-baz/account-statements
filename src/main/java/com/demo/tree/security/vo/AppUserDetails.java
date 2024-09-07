package com.demo.tree.security.vo;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDetails implements UserDetails {

    private Long id;
    private String userName;
    private String password;
    private String role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}
