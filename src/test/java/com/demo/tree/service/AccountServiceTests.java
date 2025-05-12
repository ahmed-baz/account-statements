package com.demo.tree.service;


import com.demo.tree.dto.Account;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceTests {

    @Autowired
    private AccountService accountService;

    @Test
    @Order(1)
    @DisplayName("JUnit test to create account")
    void testCreateEmployee() {
        Account account = Account.builder()
                .accountType("TEST")
                .build();
        Account savedAccount = accountService.createAccount(account);
        assertNotNull(savedAccount);
        assertNotNull(savedAccount.accountNumber());
        assertEquals("TEST", savedAccount.accountType());
    }

}
