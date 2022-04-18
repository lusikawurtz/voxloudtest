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
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        List<ImageEntity> images;

        if (image != null) {
            images = repository.findAll(Specification
                    .where(hasName(image.getName()))
                    .or(hasContentType(image.getContentType()))
                    .or(isSized(image.getSize()))
                    .or(hasReference(image.getReference())));
        } else
            images = repository.findAll();

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
        Date currentDate = new Date();

        images.forEach(image -> {
            ImageEntity newImage = mapper.map(image);
            newImage.setOwnerId(accountService.findByUsername(username).get().getId());
            newImage.setCreatedAt(currentDate);
            newImage.setUpdatedAt(currentDate);
            repository.save(newImage);
            imageEntities.add(newImage);
        });
        accountService.saveImagesToAccount(imageEntities, username);
    }

    @Override
    public void deleteImage(Long id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public void modifyImage(Long id, AccountEntity account, Image image) {
        ImageEntity resultImage = mapper.map(image);

        resultImage.setId(id);
        resultImage.setOwnerId(account.getId());
        resultImage.setCreatedAt(findById(id).getCreatedAt());
        resultImage.setUpdatedAt(new Date());
        repository.save(resultImage);
    }

    @Override
    public ImageEntity findById(Long id) {
        return repository.findById(id).get();
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
