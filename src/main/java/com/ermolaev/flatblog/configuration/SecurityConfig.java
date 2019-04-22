package com.ermolaev.flatblog.configuration;

import com.ermolaev.flatblog.model.security.Authorities;
import com.ermolaev.flatblog.service.security.JWTHeadersExchangeMatcher;
import com.ermolaev.flatblog.service.security.JWTReactiveAuthenticationManager;
import com.ermolaev.flatblog.service.security.TokenAuthenticationConverter;
import com.ermolaev.flatblog.service.security.TokenProvider;
import com.ermolaev.flatblog.service.security.UnauthorizedAuthenticationEntryPoint;
import com.ermolaev.flatblog.service.security.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final UserDetailsService userDetailsService;
  private final TokenProvider tokenProvider;

  private static final String[] AUTH_WHITELIST = {
      "/resources/**",
      "/webjars/**",
      "/auth/**",
      "/users/**",
      "/favicon.ico",
  };

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, UnauthorizedAuthenticationEntryPoint entryPoint) {

    http.httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .logout().disable();

    http
        .exceptionHandling()
        .authenticationEntryPoint(entryPoint)
        .and()
        .authorizeExchange()
        .matchers(EndpointRequest.to("health", "info"))
        .permitAll()
        .and()
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .and()
        .authorizeExchange()
        .matchers(EndpointRequest.toAnyEndpoint())
        .hasAuthority(Authorities.ROLE_ADMIN.getName())
        .and()
        .addFilterAt(webFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
        .authorizeExchange()
        .pathMatchers(AUTH_WHITELIST).permitAll()
        .anyExchange().authenticated();

    return http.build();
  }

  @Bean
  public AuthenticationWebFilter webFilter() {
    AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(repositoryReactiveAuthenticationManager());
    authenticationWebFilter.setAuthenticationConverter(new TokenAuthenticationConverter(tokenProvider));
    authenticationWebFilter.setRequiresAuthenticationMatcher(new JWTHeadersExchangeMatcher());
    authenticationWebFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
    return authenticationWebFilter;
  }

  @Bean
  public JWTReactiveAuthenticationManager repositoryReactiveAuthenticationManager() {
    return new JWTReactiveAuthenticationManager(
        userDetailsService, passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
