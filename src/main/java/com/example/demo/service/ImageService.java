package com.example.demo.service;

import com.example.demo.model.AppImage;
import com.example.demo.repo.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageUploader imageUploader;

    public ImageService(ImageRepository imageRepository, ImageUploader imageUploader) {
        this.imageRepository = imageRepository;
        this.imageUploader = imageUploader;
    }

    /*public List<AppImage> getAllImages() {
        List<AppImage> images = imageRepository.getAppImage();
        return images;
    }/**/

    public List<AppImage> getImagesByUserId(Long id) {
        List<AppImage> images = imageRepository.findByAppUser_Id(id);
        return images;
    }

    public List<AppImage> getImagesByUsername(String username) {
        List<AppImage> images = imageRepository.findByAppUser_Username(username);
        return images;
    }

    public void removeImageById(Long id, String cloudinaryId) {
        imageRepository.removeAppImageById(id);
        imageUploader.deleteImage(cloudinaryId);
    }

    public List<AppImage> findAllImages() {
        return imageRepository.findAll();
    }
}
