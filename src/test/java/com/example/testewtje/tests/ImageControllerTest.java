package com.example.testewtje.tests;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.repository.ImageRepository;
import com.example.testewtje.service.ImageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
@SpringBootTest
public class ImageControllerTest {

    @Autowired
    private ImageService service;
    @Autowired
    private ImageRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveImageTest() {
        Assertions.assertThat(service.getAllImages()).isEmpty();

        ImageEntity image = ImageEntity.builder()
                .name("doggy")
                .picSize(BigDecimal.valueOf(128))
                .reference("")
                .contentType("PNG")
                .tags(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        repository.save(image);
        Assertions.assertThat(repository.findById(1L)).isNotNull();
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void getImageByIdTest() {
        ImageEntity image = service.findById(1L);
        Assertions.assertThat(image.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void deleteImageTest() {
        ImageEntity image = repository.findById(1L).get();
        repository.delete(image);

        ImageEntity testImage = service.findById(1L);
        Assertions.assertThat(testImage).isNull();
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void createImageTest() {
        Assertions.assertThat(service.getAllImages()).isEmpty();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Image image = Image.builder()
                .name("kitty")
                .picSize(BigDecimal.valueOf(128))
                .reference("")
                .contentType("PNG")
                .tags(new ArrayList<>())
                .build();
        service.createImages(new ArrayList<>(List.of(image)), user.getUsername());

        ImageEntity imageInRepository = service.findById(1L);
        Assertions.assertThat(imageInRepository.getName()).isEqualTo(image.getName());
    }

    @Test
    @Order(5)
    public void getAllImages() {
        List<ImageEntity> imageEntities = service.getAllImages();
        Assertions.assertThat(imageEntities.size()).isGreaterThan(0);
    }

    @Test
    @Order(6)
    public void getAllImagesByProperty() {
        Pageable pageable = Pageable.ofSize(4);
        Image image = Image.builder()
                .name("kitty")
                .build();

        Page<ImageEntity> imageEntities = service.getAllImagesByProperty(image, pageable);
        Assertions.assertThat(imageEntities.getTotalElements()).isGreaterThan(0);
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void modifyImages() {
        Image image = Image.builder()
                .name("doggy")
                .build();

        service.modifyImage(1L, image);
        Assertions.assertThat(image.getName()).isEqualTo("doggy");
    }

}
