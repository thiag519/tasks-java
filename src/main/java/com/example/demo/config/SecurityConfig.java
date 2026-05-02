package com.example.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(org.springframework.security.config.Customizer.withDefaults())// ativa CORS
      .csrf(csrf -> csrf.disable()) // desativa CSRF (API REST)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/tasks/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/tasks/**").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/tasks/**").permitAll()
        .requestMatchers(HttpMethod.PATCH, "/tasks/**").permitAll()

        .anyRequest().authenticated()
      );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(Arrays.asList(
      "http://127.0.0.1:5500", // seu front local
      "http://localhost:5500",
      "https://tasks-front-dusky.vercel.app" // seu front deploy
    ));

    configuration.setAllowedMethods(Arrays.asList(
      "GET", "POST", "PUT", "DELETE", "OPTIONS"
    ));

    configuration.setAllowedHeaders(Arrays.asList(
      "Authorization",
      "Content-Type",
      "Accept",
      "x-admin-password"
    ));

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}