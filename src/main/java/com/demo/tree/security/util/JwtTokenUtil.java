package com.demo.tree.security.util;


import com.demo.tree.enums.RoleTypeEnum;
import com.demo.tree.security.vo.LoginResponse;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j2
@Component
public class JwtTokenUtil {

    @Value("${secret.key}")
    private String tokenSecretKey;
    @Value("${access.token.expiration:300}")
    private Long accessTokenExpirationTime;

    public String generateToken(final String username, final String tokenId) {
        return Jwts.builder()
                .setId(tokenId)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("tree")
                .setExpiration(getTokenExpirationDate())
                .claim("created", Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS512, tokenSecretKey).compact();
    }

    public LoginResponse prepareLoginResponse(UserDetails appUserDetails) {
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = generateToken(appUserDetails.getUsername(), accessTokenId);
        return LoginResponse.builder()
                .userName(appUserDetails.getUsername())
                .roles(getRoles(appUserDetails.getAuthorities()))
                .accessToken(accessToken)
                .build();
    }

    private List<RoleTypeEnum> getRoles(Collection<? extends GrantedAuthority> authorities) {
        List<RoleTypeEnum> roles = new ArrayList<>();
        authorities.forEach(authority -> roles.add(RoleTypeEnum.valueOf(authority.getAuthority())));
        return roles;
    }

    private Date getTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + accessTokenExpirationTime * 1000);
    }

    public String getUserNameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            log.error("Jwt claims string is empty");
        }
        return false;
    }
}
