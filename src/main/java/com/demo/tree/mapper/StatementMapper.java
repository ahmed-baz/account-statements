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

    @Mapping(source = "date", target = "date", qualifiedByName = "mapDate")
    Statement toStatement(StatementEntity entity);

    @Named("mapDate")
    default Date mapDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    List<Statement> toStatement(List<StatementEntity> entities);
}
