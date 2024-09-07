package com.demo.tree.validation;

import com.demo.tree.dto.AccountFilterRequest;

public interface AccValidationService {

    void validateFilterRequest(AccountFilterRequest filterRequest);
}
