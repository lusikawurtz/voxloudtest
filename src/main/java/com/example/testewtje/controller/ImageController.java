package com.example.testewtje.controller;

import com.example.testewtje.model.dto.Image;
import com.example.testewtje.model.entyties.AccountEntity;
import com.example.testewtje.model.entyties.ImageEntity;
import com.example.testewtje.service.AccountService;
import com.example.testewtje.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final AccountService accountService;

    @GetMapping("/all")
    public Page<ImageEntity> getAllImages(Pageable pageable) {
        return imageService.getAllImagesByProperty(null, pageable);
    }

    @GetMapping("/filter")
    public Page<ImageEntity> getAllImagesByProperty(Image image, Pageable pageable) {
        return imageService.getAllImagesByProperty(image, pageable);
    }

    @PostMapping("/create")
    public ResponseEntity createImages(@RequestBody List<Image> images, @RequestParam String username) {
        try {
            imageService.createImages(images, username);
            return ResponseEntity.ok().build();
        } catch (HttpMessageNotReadableException e) {
            return ResponseEntity.badRequest().body("sus");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify/{id}/{username}")
    public ResponseEntity modifyImage(@PathVariable("id") Long id, @PathVariable("username") String username, @RequestBody Image image) {
        AccountEntity account = accountService.findByUsername(username).get();
        if (account.getImages().contains(imageService.findById(id))) {
            imageService.modifyImage(id, account, image);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
