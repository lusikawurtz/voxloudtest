package com.example.testewtje.controller;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/all")
    public Page<ImageEntity> getAllImages(Pageable pageable) {
        return imageService.getAllImagesByProperty(null, pageable);
    }

    @GetMapping("/filter")
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        return imageService.getAllImagesByProperty(image, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity createImages(@RequestBody List<Image> images) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            imageService.createImages(images, user.getUsername());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity modifyImage(@PathVariable("id") Long id, @RequestBody Image image) {
        try {
            imageService.modifyImage(id, image);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
