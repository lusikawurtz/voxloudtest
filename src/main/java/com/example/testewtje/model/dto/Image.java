package com.example.testewtje.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Image {

    private String name;
    private String contentType;
    private BigDecimal picSize;
    private String reference;
    private List<Tag> tags;

}
