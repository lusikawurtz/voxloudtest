package com.example.testewtje.tests;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.repository.AccountRepository;
import com.example.testewtje.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@SpringBootTest
public class AccountControllerTest {

    @Autowired
    private AccountService service;
    @Autowired
    private AccountRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveAccountTest() {
        Assertions.assertThat(service.getAllAccounts()).isEmpty();

        AccountEntity account = AccountEntity.builder()
                .username("lusika")
                .password("")
                .nickname("Lusmem")
                .images(new ArrayList<>())
                .build();

        repository.save(account);
        Assertions.assertThat(repository.findById(1L)).isNotNull();
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getAccountByIdTest() {
        AccountEntity account = service.findById(1L);
        Assertions.assertThat(account.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void getAccountByUsernameTest() {
        AccountEntity account = service.findByUsername("lusika");
        Assertions.assertThat(account.getUsername()).isEqualTo("lusika");
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void deleteEmployeeTest() {
        AccountEntity account = repository.findById(1L).get();
        repository.delete(account);

        AccountEntity testAccount = service.findByUsername("lusika");
        Assertions.assertThat(testAccount).isNull();
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void createAccountTest() {
        Assertions.assertThat(service.getAllAccounts()).isEmpty();

        Account account = Account.builder()
                .username("kto")
                .password("")
                .nickname("chto")
                .images(new ArrayList<>())
                .build();

        service.createAccount(account);
        AccountEntity accountInRepository = service.findByUsername(account.getUsername());
        Assertions.assertThat(accountInRepository.getUsername()).isEqualTo(account.getUsername());
    }

    @Test
    @Order(6)
    public void getAllAccounts() {
        List<AccountEntity> accountEntities = service.getAllAccounts();
        Assertions.assertThat(accountEntities.size()).isGreaterThan(0);
    }

    @Test
    @Order(7)
    public void getAllAccountsByProperty() {

        Pageable pageable = Pageable.ofSize(4);
        Account account = Account.builder()
                .username("kto")
                .build();

        Page<AccountEntity> accountEntities = service.getAllAccountsByProperty(account, pageable);
        Assertions.assertThat(accountEntities.getTotalElements()).isGreaterThan(0);
    }

}
