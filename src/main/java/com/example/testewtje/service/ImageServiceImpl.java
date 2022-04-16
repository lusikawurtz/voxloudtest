package com.example.testewtje.service;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.model.mapper.ImageMapper;
import com.example.testewtje.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AccountService accountService;
    private final ImageRepository repository;
    private final ImageMapper mapper;


    @Override
    public Page<ImageEntity> getAllImages(Pageable pageable) {
        List<ImageEntity> images = repository.findAll();

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        List<ImageEntity> paged = images
                .stream()
                .skip((long) pageable.getPageSize() * page)
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paged, pageable, images.size());
    }

    @Override
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        List<ImageEntity> images = repository.findAll(Specification
                .where(hasName(image.getName()))
                .or(hasContentType(image.getContentType()))
                .or(isSized(image.getSize()))
                .or(hasReference(image.getReference())));

        int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() - 1 : 0;
        List<ImageEntity> paged = images
                .stream()
                .skip((long) pageable.getPageSize() * page)
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paged, pageable, images.size());
    }

    @Override
    public void createImages(List<Image> images, String username) {
        List<ImageEntity> imageEntities = new ArrayList<>();
        images.forEach(image -> {
            ImageEntity newImage = mapper.map(image);
            newImage.setOwnerId(accountService.findByUsername(username).get());
            newImage.setCreatedAt(new Date());
            newImage.setUpdatedAt(new Date());
            repository.save(newImage);
            imageEntities.add(newImage);
        });
        accountService.saveImagesToAccount(imageEntities);
    }

    @Override
    public void deleteImage(Long id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public void modifyImage(Long id, Image image, String username) {
        AccountEntity account = accountService.findByUsername(username).get();

        if (account.getImages().contains(repository.findById(id).get())) {
            ImageEntity oldImage = mapper.map(image);
            oldImage.setId(id);
            oldImage.setUpdatedAt(new Date());
            repository.save(oldImage);
            accountService.saveImagesToAccount(List.of(oldImage));
        }
    }


    static Specification<ImageEntity> hasName(String name) {
        return (image, cq, cb) -> cb.equal(image.get("name"), name);
    }

    static Specification<ImageEntity> hasContentType(String contentType) {
        return (image, cq, cb) -> cb.equal(image.get("contentType"), contentType);
    }

    static Specification<ImageEntity> isSized(BigDecimal size) {
        return (image, cq, cb) -> cb.equal(image.get("size"), size);
    }

    static Specification<ImageEntity> hasReference(String reference) {
        return (image, cq, cb) -> cb.equal(image.get("reference"), reference);
    }

}
