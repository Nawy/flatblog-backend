package com.ermolaev.flatblog.configuration;

import com.ermolaev.flatblog.service.UserArticleDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  @Bean
  public ReactiveAuthenticationManager authenticationManager(
      UserArticleDetailsService userDetailsService) {
    UserDetailsRepositoryReactiveAuthenticationManager manager =
        new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    manager.setPasswordEncoder(passwordEncoder());
    return manager;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http) {
    http.csrf().disable()
        .cors().disable()
        .formLogin()
        .loginPage("/login")
        .and()
        .authorizeExchange()
        .pathMatchers(HttpMethod.POST, "/users").permitAll()
        .pathMatchers(HttpMethod.GET, "/users").permitAll()
        .and()
        .httpBasic();
    return http.build();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder("long main word");
  }
}
