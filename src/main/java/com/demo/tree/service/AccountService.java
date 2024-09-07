package com.demo.tree.service;

import com.demo.tree.dto.AccountFilterRequest;
import com.demo.tree.dto.Statement;

import java.util.List;

public interface AccountService {

    List<Statement> filterStatements(AccountFilterRequest filterRequest);
}
