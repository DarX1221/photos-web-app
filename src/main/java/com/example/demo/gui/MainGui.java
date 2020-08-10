package com.example.demo.gui;

import com.example.demo.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("")
public class MainGui extends VerticalLayout {
    private UserService userService;

    public MainGui(UserService userService) {
        this.userService = userService;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if(userService.findByUsername(username) != null) {
            UI.getCurrent().getPage().setLocation("/gallery");
        }
        else {
            UI.getCurrent().getPage().setLocation("/registration");
        }
    }
}
