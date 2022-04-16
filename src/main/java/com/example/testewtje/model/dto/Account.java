package com.example.testewtje.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Account {

    private String username;
    private String password;
    private String name;
    private List<Image> images;

}
