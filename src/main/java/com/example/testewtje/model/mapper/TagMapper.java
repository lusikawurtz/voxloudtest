package com.example.testewtje.model.mapper;

import com.example.testewtje.model.dto.Tag;
import com.example.testewtje.model.entyties.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagEntity.class, Tag.class})
public interface TagMapper {

    TagEntity map(Tag tag);

}
