package com.example.demo.gui;


import com.example.demo.model.AppImage;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;


@Route("gallery")
public class GalleryGui extends VerticalLayout {
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public GalleryGui(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;

        Button buttonUploadImage = new Button("Upload Image");
        buttonUploadImage.addClickListener(event -> UI.getCurrent().getPage().setLocation("/upload_image"));

        add(buttonUploadImage);

        Button buttonLogout = new Button("Logout");
        buttonLogout.addClickListener(event -> UI.getCurrent().getPage().setLocation("/logout"));
        add(buttonLogout);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(userService.findByUsername(username).getRole().equals("ROLE_ADMIN")) {
            Button buttonGalleryAllImage = new Button("All Images (Admin)");
            buttonGalleryAllImage.addClickListener(event ->
            {
                UI.getCurrent().getPage().setLocation("/all_images");
            });
            add(buttonGalleryAllImage);
        }


        showImages(username);
    }


    public void showImages(String username) {
        List<AppImage> images = imageService.getImagesByUsername(username);

        images.forEach(image -> {
            Image imageView = new Image(image.getAdress(), "Loading error");
            add(imageView);


            Button buttonDel = new Button("Delete");
            buttonDel.addClickListener(event -> {
                imageService.removeImageById(image.getId(), image.getCloudinaryId());
                UI.getCurrent().getPage().reload();
            });
            add(buttonDel);
        });
    }
}
