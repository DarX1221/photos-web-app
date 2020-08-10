package com.example.demo.gui;

import com.example.demo.service.ImageUploader;
import com.example.demo.model.AppImage;
import com.example.demo.model.AppUser;
import com.example.demo.repo.ImageRepository;
import com.example.demo.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Route("upload_image")
public class UploadGui extends VerticalLayout {

    private ImageUploader imageUploader;
    private ImageRepository imageRepository;
    private UserService userService;

    @Autowired
    public UploadGui(ImageUploader imageUploader,
                     ImageRepository imageRepository,
                     UserService userService) {
        this.imageUploader = imageUploader;
        this.imageRepository = imageRepository;
        this.userService = userService;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Button buttonGallery = new Button("Gallery");
        buttonGallery.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("/gallery");
        });
        add(buttonGallery);

        Button buttonLogout = new Button("Logout");
        buttonLogout.addClickListener(event -> {
            UI.getCurrent().getPage().setLocation("/logout");
        });
        add(buttonLogout);

        if(userService.findByUsername(username).getRole().equals("ROLE_ADMIN")) {
            Button buttonGalleryAllImage = new Button("All Images (Admin)");
            buttonGalleryAllImage.addClickListener(event ->
            {
                UI.getCurrent().getPage().setLocation("/all_images");
            });
            add(buttonGalleryAllImage);
        }

        Label label = new Label();
        TextField textField = new TextField();
        Button button = new Button("Upload");

        button.addClickListener(buttonClickEvent -> {
            String[] uploaderData = imageUploader.uploadImage(textField.getValue());
            String imageUrl = uploaderData[0];
            String cloudinaryId = uploaderData[1];
            label.setText("Image added to your gallery");

            if(!imageUrl.equals("error")) {
                // Image is a view (Vaadin)
                Image image = new Image(imageUrl, "Error!");

                // AppImage - image to save in DB, AppImage is not a file is object(url, username)
                AppUser appUser = userService.findByUsername(username);

                AppImage appImage = new AppImage(imageUrl, cloudinaryId , appUser);
                imageRepository.save(appImage);
                add(label);
                add(image);
            }
        });

        add(textField);
        Text text = new Text("Please paste local file address (for example: \"/home/user/Obrazy/image.jpg\")");
        add(text);
        add(button);
    }
}