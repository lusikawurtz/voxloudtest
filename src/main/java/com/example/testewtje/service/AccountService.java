package com.example.testewtje.service;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.model.entyties.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Page<AccountEntity> getAllAccounts(Pageable pageable);

    Page<AccountEntity> getAllAccountsByProperty(Account account, Pageable pageable);

    void createAccount(Account account);

    void deleteAccount(Long id);

    Optional<AccountEntity> findByUsername(String username);

    void saveImagesToAccount(List<ImageEntity> images, String username);

}
