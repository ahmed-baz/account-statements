package com.demo.tree.mapper;

import com.demo.tree.dto.Account;
import com.demo.tree.exceptions.AlgorithmException;
import com.demo.tree.model.AccountEntity;
import org.mapstruct.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "accountNumber", target = "accountNumber", qualifiedByName = "hashAccountNumber")
    Account toAccount(AccountEntity entity);

    List<Account> toAccount(List<AccountEntity> entities);

    @Named("hashAccountNumber")
    default String hashAccountNumber(String accountNumber) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new AlgorithmException("SHA-256 algorithm not available");
        }
        messageDigest.update(accountNumber.getBytes());
        return new String(messageDigest.digest());
    }


}
