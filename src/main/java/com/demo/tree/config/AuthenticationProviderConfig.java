package com.demo.tree.config;


import com.demo.tree.enums.RoleTypeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class AuthenticationProviderConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("{noop}user")
                .authorities(new SimpleGrantedAuthority(RoleTypeEnum.USER.name()))
                .build());
        manager.createUser(User.withUsername("admin")
                .password("{noop}admin")
                .authorities(new SimpleGrantedAuthority(RoleTypeEnum.ADMIN.name()))
                .build());
        return manager;
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

}
