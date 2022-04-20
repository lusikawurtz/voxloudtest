package com.example.testewtje.service;

import com.example.testewtje.model.dto.Tag;
import com.example.testewtje.model.entyties.TagEntity;

public interface TagService {

    void saveTagEntity(TagEntity tag);

    void saveTag(Tag tag);

    boolean isSameTagExist(String name);

    TagEntity findByName(String name);

}
