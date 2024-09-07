package com.demo.tree.controller;


import com.demo.tree.dto.AccountFilterRequest;
import com.demo.tree.dto.AppResponse;
import com.demo.tree.dto.Statement;
import com.demo.tree.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statements")
@RequiredArgsConstructor
public class StatementController {

    private final AccountService accountService;

    @PostMapping("/filter")
    public AppResponse<List<Statement>> filterStatements(@Valid @RequestBody AccountFilterRequest request) {
        return AppResponse.ok(accountService.filterStatements(request));
    }

}
