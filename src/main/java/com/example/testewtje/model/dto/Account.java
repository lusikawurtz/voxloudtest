package com.example.testewtje.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Account {

    private String username;
    private String password;
    private String nickname;
    private List<Image> images;

}
