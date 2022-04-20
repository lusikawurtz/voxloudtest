package com.example.testewtje.service;

import com.example.testewtje.model.dto.Tag;
import com.example.testewtje.model.entyties.TagEntity;
import com.example.testewtje.model.mapper.TagMapper;
import com.example.testewtje.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagMapper tagMapper;

    @Override
    public void saveTagEntity(TagEntity tag) {
        if (!isSameTagExist(tag.getName()))
            repository.save(tag);
    }

    @Override
    public void saveTag(Tag tag) {
        if (!isSameTagExist(tag.getName())) {
            TagEntity tagEntity = tagMapper.map(tag);
            repository.save(tagEntity);
        }
    }

    @Override
    public boolean isSameTagExist(String name) {
        return repository.findByName(name) != null;
    }

    @Override
    public TagEntity findByName(String name) {
        return repository.findByName(name);
    }

}
