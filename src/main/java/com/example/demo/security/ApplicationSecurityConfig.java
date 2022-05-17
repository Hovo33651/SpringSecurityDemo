package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserRole.*;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .antMatchers(GET, "/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers(GET, "/students/**").hasRole(STUDENT.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("ueierfhwufhuiwrygi5g86");

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails poxos = User.builder()
                .username("poxos")
                .password(passwordEncoder.encode("root"))
                .authorities(STUDENT.grantedAuthorities())
                // .roles(STUDENT.name())
                .build();

        UserDetails petros = User.builder()
                .username("petros")
                .password(passwordEncoder.encode("root"))
                .authorities(ADMIN.grantedAuthorities())
                //   .roles(ADMIN.name())
                .build();

        UserDetails armen = User.builder()
                .username("armen")
                .password(passwordEncoder.encode("root"))
                .authorities(ADMINTRAINEE.grantedAuthorities())
                //  .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(
                poxos,
                petros,
                armen);
    }
}
