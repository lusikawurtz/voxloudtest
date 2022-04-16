package com.example.testewtje.model.mapper;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.entyties.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "images", source = "images")
    AccountEntity map(Account account);

}