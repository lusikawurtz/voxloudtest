package com.example.testewtje.service;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.model.entyties.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {

    List<AccountEntity> getAllAccounts();

    Page<AccountEntity> getAllAccounts(Pageable pageable);

    Page<AccountEntity> getAllAccountsByProperty(Account account, Pageable pageable);

    void createAccount(Account account);

    void deleteAccount(Long id);

    AccountEntity findById(Long id);

    AccountEntity findByUsername(String username);

    void saveImagesToAccount(List<ImageEntity> images, String username);

}
