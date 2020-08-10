package com.example.demo.gui;

import com.example.demo.service.UserService;
import com.example.demo.model.AppUser;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("registration")
public class RegistrationGui extends VerticalLayout {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public RegistrationGui(PasswordEncoder passwordEncoder,
                           UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

        Button loginButton = new Button("Login");
        loginButton.addClickListener(event -> UI.getCurrent().getPage().setLocation("/login"));
        add(loginButton);

        Text text1 = new Text("Sign in or register");
        add(text1);

        TextField name = new TextField();
        Text nameTxt = new Text("Name");
        add(name);
        add(nameTxt);

        EmailField email = new EmailField();
        email.setValueChangeMode(ValueChangeMode.EAGER);
        Text emailTxt = new Text("Email");
        add(email);
        add(emailTxt);

        PasswordField password = new PasswordField();
        Text passTxt = new Text("Password");
        add(password);
        add(passTxt);

        Button registerButton = new Button("Register");
        registerButton.addClickListener(event -> {
            String appUsername = name.getValue();
            String appEmail = email.getValue();
            String appPassword = passwordEncoder.encode(password.getValue());
            AppUser appUser = new AppUser(appUsername, appEmail, appPassword, "ROLE_USER");
            userService.saveUser(appUser);
            UI.getCurrent().getPage().setLocation("/upload_image");
        });
        add(registerButton);

    }
}
