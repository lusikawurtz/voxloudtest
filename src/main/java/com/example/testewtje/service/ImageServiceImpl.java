package com.example.testewtje.service;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.dto.Tag;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.model.entyties.TagEntity;
import com.example.testewtje.model.mapper.ImageMapper;
import com.example.testewtje.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AccountService accountService;
    private final TagService tagService;

    private final ImageRepository repository;
    private final ImageMapper mapper;


    @Override
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        List<ImageEntity> images;

        if (image.getTags() != null)
            images = hasTags(image.getTags());
        else if (image.getName() != null)
            images = repository.findAll(Specification.where(hasName(image.getName())));
        else if (image.getContentType() != null)
            images = repository.findAll(Specification.where(hasContentType(image.getContentType())));
        else if (image.getPicSize() != null)
            images = repository.findAll(Specification.where(isSized(image.getPicSize())));
        else if (image.getReference() != null)
            images = repository.findAll(Specification.where((hasReference(image.getReference()))));
        else
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
            newImage.setOwnerId(accountService.findByUsername(username).getId());
            newImage.setCreatedAt(currentDate);
            newImage.setUpdatedAt(currentDate);
            newImage.setTags(new ArrayList<>());

            image.getTags().forEach(tag -> {
                tagService.saveTag(tag);
                newImage.getTags().add(tagService.findByName(tag.getName()));
            });

            repository.save(newImage);
            imageEntities.add(newImage);
        });
        accountService.saveImagesToAccount(imageEntities, username);
    }

    @Override
    public void deleteImage(Long id) {
        try {
            repository.delete(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void modifyImage(Long id, Image image) {
        ImageEntity imageEntity = findById(id);

        if (((User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername().equals(
                accountService.findById(imageEntity.getOwnerId()).getUsername())) {
            ImageEntity resultImage = mapper.map(image);

            resultImage.setId(id);
            resultImage.setOwnerId(imageEntity.getOwnerId());
            resultImage.setCreatedAt(imageEntity.getCreatedAt());
            resultImage.setUpdatedAt(new Date());
            repository.save(resultImage);
        }
    }

    @Override
    public ImageEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    private List<ImageEntity> hasTags(List<Tag> tags) {
        try {
            List<TagEntity> tagEntities = new ArrayList<>();
            tags.forEach(tag -> tagEntities.add(tagService.findByName(tag.getName())));
            return repository.findByTagsIn(tagEntities);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static Specification<ImageEntity> hasName(String name) {
        return (image, cq, cb) -> cb.equal(image.get("name"), name);
    }

    static Specification<ImageEntity> hasContentType(String contentType) {
        return (image, cq, cb) -> cb.equal(image.get("contentType"), contentType);
    }

    static Specification<ImageEntity> isSized(BigDecimal picSize) {
        return (image, cq, cb) -> cb.equal(image.get("picSize"), picSize);
    }

    static Specification<ImageEntity> hasReference(String reference) {
        return (image, cq, cb) -> cb.equal(image.get("reference"), reference);
    }

}
