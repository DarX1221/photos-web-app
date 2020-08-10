package com.example.demo.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AppImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adress;
    // cloudinaryId is public_id use on cloudinary.com (need to delete image)
    @NotNull
    private String cloudinaryId;
    @ManyToOne
    private AppUser appUser;

    public AppImage() {
    }

    public AppImage(String adress, String cloudinaryId, AppUser appUser) {
        this.adress = adress;
        this.cloudinaryId = cloudinaryId;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }
}
