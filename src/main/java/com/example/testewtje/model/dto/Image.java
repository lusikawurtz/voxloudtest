package com.example.testewtje.model.dto;

import com.example.testewtje.model.entyties.TagEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Image {

    private String name;
    private String contentType;
    private BigDecimal size;
    private String reference;
    private Set<TagEntity> tags;

}
