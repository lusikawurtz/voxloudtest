package com.example.testewtje.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Account {

    private String username;
    private String password;
    private String name;
    private List<Image> images;

}
