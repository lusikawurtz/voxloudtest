package com.example.testewtje.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class Image {

    private String name;
    private String contentType;
    private BigDecimal size;
    private String reference;
    private String tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(name, image.name) && Objects.equals(contentType, image.contentType) && Objects.equals(size, image.size) && Objects.equals(reference, image.reference) && Objects.equals(tags, image.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contentType, size, reference, tags);
    }

}
