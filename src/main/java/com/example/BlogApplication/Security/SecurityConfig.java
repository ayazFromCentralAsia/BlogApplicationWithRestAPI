package com.example.BlogApplication.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {return new MyUserDetailsService(); }


    @Bean
    public AuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/blog").permitAll()
                        .requestMatchers("/blog/").permitAll()
                        .requestMatchers("/comment").permitAll()
                        .requestMatchers("/comment/").permitAll()
                        .requestMatchers("/user").permitAll()
                        .requestMatchers("/user/").permitAll()
                        .requestMatchers("/stylesUser.css").permitAll()
                        .requestMatchers("/userView").hasRole("USER")
                        .anyRequest().hasRole("ADMIN"))
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

}
