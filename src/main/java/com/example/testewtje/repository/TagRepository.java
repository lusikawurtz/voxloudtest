package com.example.testewtje.repository;

import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.model.entyties.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>, JpaSpecificationExecutor<ImageEntity> {

    TagEntity findByName(String name);

}
