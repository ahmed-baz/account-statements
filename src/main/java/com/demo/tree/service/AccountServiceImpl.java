package com.demo.tree.service;

import com.demo.tree.dto.AccountFilterRequest;
import com.demo.tree.dto.Statement;
import com.demo.tree.exceptions.AccountNotFoundException;
import com.demo.tree.mapper.StatementMapper;
import com.demo.tree.model.AccountEntity;
import com.demo.tree.model.StatementEntity;
import com.demo.tree.repo.AccountRepo;
import com.demo.tree.repo.StatementRepo;
import com.demo.tree.validation.AccValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final StatementMapper statementMapper;
    private final AccValidationService accValidationService;
    private final StatementRepo statementRepo;

    @Override
    public List<Statement> filterStatements(AccountFilterRequest filterRequest) {
        accValidationService.validateFilterRequest(filterRequest);
        AccountEntity accountEntity = findAccount(filterRequest.accountId());
        var statements = accountEntity.getStatements();
        var list = statements.stream().map(statementMapper::toStatement).toList();
        if (filterRequest.amountFrom() != null && filterRequest.amountTo() != null) {
            list = list.stream().filter(statement ->
                    (statement.amount() > filterRequest.amountFrom()
                            && statement.amount() < filterRequest.amountTo())).toList();
        }
        if (filterRequest.dateFrom() != null && filterRequest.dateTo() != null) {
            list = list.stream().filter(statement ->
                    (statement.date().after(filterRequest.dateFrom())
                            && statement.date().before(filterRequest.dateTo()))).toList();
        }
        return list;
    }

    @Override
    public void createStatement(Statement statement) {
        var statementEntity = statementMapper.toEntity(statement);
        AccountEntity accountEntity = findAccount(statement.account().id());
        statementEntity.setAccount(accountEntity);
        statementRepo.save(statementEntity);
    }

    private AccountEntity findAccount(Long id) {
        return accountRepo.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
