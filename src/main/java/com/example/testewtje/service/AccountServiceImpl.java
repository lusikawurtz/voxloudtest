package com.example.testewtje.service;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.model.mapper.AccountMapper;
import com.example.testewtje.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public Page<AccountEntity> getAllAccounts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<AccountEntity> getAllAccountsByProperty(Account account, Pageable pageable) {
        List<AccountEntity> accounts = repository.findAll(Specification
                .where(hasName(account.getName()))
                .or(hasUsername(account.getUsername())));

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        List<AccountEntity> paged = accounts
                .stream()
                .skip((long) pageable.getPageSize() * page)
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paged, pageable, accounts.size());
    }

    @Override
    public void createAccount(Account account) {
        AccountEntity newAccount = mapper.map(account);
        newAccount.setCreatedAt(new Date());
        newAccount.setUpdatedAt(new Date());
        repository.save(newAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public Optional<AccountEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void saveImagesToAccount(List<ImageEntity> images) {

    }


    static Specification<AccountEntity> hasName(String name) {
        return (image, cq, cb) -> cb.equal(image.get("name"), name);
    }

    static Specification<AccountEntity> hasUsername(String username) {
        return (image, cq, cb) -> cb.equal(image.get("username"), username);
    }

}
