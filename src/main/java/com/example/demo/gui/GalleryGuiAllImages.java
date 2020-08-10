package com.example.demo.gui;


import com.example.demo.model.AppImage;
import com.example.demo.repo.ImageRepository;
import com.example.demo.service.ImageService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@Route("all_images")
public class GalleryGuiAllImages extends VerticalLayout {
    private final ImageService imageService;

    @Autowired
    public GalleryGuiAllImages(final ImageService imageService) {
        this.imageService = imageService;

        Button buttonLogout = new Button("Logout");
        buttonLogout.addClickListener(event ->
        {
            UI.getCurrent().getPage().setLocation("/logout");
        });
        add(buttonLogout);

        Button buttonGallery = new Button("Gallery");
        buttonGallery.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("/gallery");
        });
        add(buttonGallery);

        Button buttonUploadImage = new Button("Upload Image");
        buttonUploadImage.addClickListener(event -> UI.getCurrent().getPage().setLocation("/upload_image"));
        add(buttonUploadImage);


        List<AppImage> images = imageService.findAllImages();

        images.forEach(image -> {
            if (image.getAdress() != null) {
                Image imageView = new Image(image.getAdress(), "Loading error");
                add(imageView);

                Button buttonDel = new Button("Delete");
                buttonDel.addClickListener(event -> {
                    imageService.removeImageById(image.getId(), image.getCloudinaryId());
                    UI.getCurrent().getPage().reload();
                });
                add(buttonDel);
            }
        });
    }

}
