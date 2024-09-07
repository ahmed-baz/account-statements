package com.demo.tree.validation;

import com.demo.tree.dto.AccountFilterRequest;
import com.demo.tree.enums.RoleTypeEnum;
import com.demo.tree.exceptions.UnauthorizedUserException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Log4j2
@Service
public class AccValidationServiceImpl implements AccValidationService {

    @Override
    public void validateFilterRequest(AccountFilterRequest filterRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority(RoleTypeEnum.USER.name()))
            && (filterRequest.dateFrom() != null || filterRequest.dateTo() != null
                || filterRequest.amountFrom() != null || filterRequest.amountTo() != null)) {
            UnauthorizedUserException ex = new UnauthorizedUserException("Unauthorized user, filter parameters are not allowed");
            log.error(ex);
            throw ex;
        }
    }
}
