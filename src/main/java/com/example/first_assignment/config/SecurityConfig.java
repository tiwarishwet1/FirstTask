package com.example.first_assignment.config;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.example.first_assignment.security.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // =========================================================
    // AUTHENTICATION PROVIDER
    // =========================================================

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    // =========================================================
    // JWT ROLE CONVERTER
    // =========================================================

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        // Our JWT contains:
        //
        // "role": "ADMIN"
        //
        // Convert that into:
        //
        // ROLE_ADMIN
        //
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    // =========================================================
    // SECURITY FILTER CHAIN
    // =========================================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // Disable CSRF because this is a REST API
            .csrf(csrf -> csrf.disable())

            // JWT authentication is stateless
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            // =================================================
            // AUTHORIZATION RULES
            // =================================================
            .authorizeHttpRequests(auth -> auth

                // Login/register endpoints are public
                .requestMatchers("/auth/**").permitAll()

                // ---------------------------------------------
                // ADMIN ONLY
                // ---------------------------------------------

                .requestMatchers(
                        HttpMethod.POST,
                        "/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/employees/**"
                ).hasRole("ADMIN")

                // ---------------------------------------------
                // GET
                // ---------------------------------------------
                //
                // Both ADMIN and EMPLOYEE can reach GET.
                //
                // The EmployeeService performs the ownership
                // check for EMPLOYEE.
                //
                .requestMatchers(
                        HttpMethod.GET,
                        "/employees/**"
                ).authenticated()

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // =================================================
            // JWT RESOURCE SERVER
            // =================================================
            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwt ->
                            jwt.jwtAuthenticationConverter(
                                    jwtAuthenticationConverter())
                    )
            );

        return http.build();
    }

    // =========================================================
    // AUTHENTICATION MANAGER
    // =========================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationProvider authenticationProvider) {

        return new ProviderManager(authenticationProvider);
    }

    // =========================================================
    // JWT DECODER
    // =========================================================

    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKey key =
                new javax.crypto.spec.SecretKeySpec(
                        JwtService.SECRET.getBytes(
                                StandardCharsets.UTF_8),
                        "HmacSHA256"
                );

        return NimbusJwtDecoder
                .withSecretKey(key)
                .build();
    }
}