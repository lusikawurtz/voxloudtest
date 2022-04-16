package com.example.testewtje.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class Account {

    private String username;
    private String password;
    private String name;
    private List<Image> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(password, account.password) && Objects.equals(name, account.name) && Objects.equals(images, account.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, images);
    }

}
