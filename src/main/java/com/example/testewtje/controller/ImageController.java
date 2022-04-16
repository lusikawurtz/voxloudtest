package com.example.testewtje.controller;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/all")
    public Page<ImageEntity> getAllImages(Pageable pageable) {
        return imageService.getAllImages(pageable);
    }

    @GetMapping("/filter")
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        return imageService.getAllImagesByProperty(image, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity createImages(@RequestBody List<Image> images, @RequestParam String username) {
        imageService.createImages(images, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

}
