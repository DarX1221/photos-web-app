package com.example.demo;

import com.example.demo.model.AppUser;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailServiceImpl userDetailService;
    private UserRepository userRepository;

    @Autowired
    public WebSecurityConfig(UserDetailServiceImpl userDetailService, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/all_images").hasRole("ADMIN")
                .antMatchers("/upload_image").hasAnyRole("USER", "ADMIN")
                .antMatchers("/gallery").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @EventListener(ApplicationReadyEvent.class)
    public void addUsers() {
        AppUser appUser = new AppUser("Jan","some@mail.com", passwordEncoder().encode("123"), "ROLE_USER");
        userRepository.save(appUser);
        AppUser appUser2 = new AppUser("Janusz", "some@mail.com", passwordEncoder().encode("password"), "ROLE_USER");
        userRepository.save(appUser2);
        AppUser appUser3 = new AppUser("Admin", "some@mail.com", passwordEncoder().encode("pass123"), "ROLE_ADMIN");
        userRepository.save(appUser3);
    }

}
