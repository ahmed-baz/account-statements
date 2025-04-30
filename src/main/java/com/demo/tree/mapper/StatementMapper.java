package com.demo.tree.mapper;

import com.demo.tree.dto.Statement;
import com.demo.tree.model.StatementEntity;
import org.mapstruct.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = AccountMapper.class)
public interface StatementMapper {

    Statement toStatement(StatementEntity entity);

    StatementEntity toEntity(Statement entity);

    List<Statement> toStatement(List<StatementEntity> entities);
}
