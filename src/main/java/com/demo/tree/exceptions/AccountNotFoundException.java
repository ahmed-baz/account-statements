package com.demo.tree.exceptions;

import static java.lang.String.format;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountId) {
        super(format("Account %d does not exist", accountId));
    }
}
