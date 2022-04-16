package com.example.testewtje.model.mapper;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImageEntity.class, Image.class})
public interface ImageMapper {

    ImageEntity map(Image account);

}
