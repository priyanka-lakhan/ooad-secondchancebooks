package com.ooad.secondchance.config;

import com.ooad.secondchance.auth.JwtTokenFilterConfigure;
import com.ooad.secondchance.auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Priyanka on 3/13/21.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/thumbnail").permitAll()
                .antMatchers("/users/resetpassword").permitAll()
                .antMatchers("/public/**", "/webapp/assets/books/**", "/resources/**","/resources/public/**", "/resources/public/books/**").permitAll()
                .antMatchers("/assets/books/**", "/js/**", "/img/**","/assets/**", "**/favicon.ico").permitAll()
                .antMatchers("/books/**").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        // Apply JWT
        http.apply(new JwtTokenFilterConfigure(jwtTokenProvider));

        // Optional, if you want to test the API from a browser
        // http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
