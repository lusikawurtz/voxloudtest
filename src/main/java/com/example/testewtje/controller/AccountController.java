package com.example.testewtje.controller;

import com.example.testewtje.model.dto.Account;
import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.service.AccountService;
import com.example.testewtje.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ImageService imageService;

    @GetMapping("/all")
    public Page<AccountEntity> getAllAccounts(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }

    @GetMapping("/filter")
    public Page<AccountEntity> getAllAccountsByProperty(Account account, Pageable pageable) {
        return accountService.getAllAccountsByProperty(account, pageable);
    }

    @PostMapping ("/create")
    public ResponseEntity createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/{id}/{username}")
    public ResponseEntity modifyImage(@PathVariable("id") Long id, @PathVariable("username") String username, @RequestBody Image image) {
        imageService.modifyImage(id, image, username);
        return ResponseEntity.ok().build();
    }

}
