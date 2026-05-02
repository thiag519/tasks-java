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
        // 🔥 libera TUDO relacionado a tasks
      .requestMatchers("/tasks").permitAll()
      .requestMatchers("/tasks/").permitAll()
      .requestMatchers("/tasks/**").permitAll()

      // 🔥 libera preflight
      .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

      // 🔥 (TESTE) libera tudo temporariamente
      .anyRequest().permitAll()
      );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOriginPatterns(Arrays.asList("*"));

    configuration.setAllowedMethods(Arrays.asList(
      "GET", "POST", "PUT", "DELETE", "OPTIONS"
    ));
    configuration.setAllowedHeaders(Arrays.asList("*"));

    configuration.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}