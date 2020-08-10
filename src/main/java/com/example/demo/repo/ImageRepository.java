package com.example.demo.repo;

import com.example.demo.model.AppImage;
import com.example.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ImageRepository extends JpaRepository<AppImage, Long> {

    List<AppImage> findByAppUser_Id(Long appUserId);
    List<AppImage> findByAppUser_Username(String username);

    List<AppImage> findAll();

    @Transactional
    void removeAppImageById(Long id);
}
