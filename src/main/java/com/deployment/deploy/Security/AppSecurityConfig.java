package com.deployment.deploy.Security;

import com.deployment.deploy.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();}

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); // set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); // set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("ADMIN")
                                .requestMatchers("/add-user/**").hasRole("ADMIN")
                                .requestMatchers("/delete-user/**").hasRole("ADMIN")
                                .requestMatchers("/update-user/**").hasRole("ADMIN")
                                .requestMatchers("/delete-client").hasRole("ADMIN")
                                .requestMatchers("/update-receipt").hasRole("ADMIN")
                                .requestMatchers("/delete-receipt").hasRole("ADMIN")
                                .requestMatchers("/deleting-client-page").hasRole("ADMIN")
                                .requestMatchers("/resources/**").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin(form ->
                        form
                                .loginPage("/authentification")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()

                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );
        return http.build();
    }
}
