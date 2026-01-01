package com.abdus.hospitalmanagement.security;


import com.abdus.hospitalmanagement.entity.type.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.config.AuditingConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.abdus.hospitalmanagement.entity.type.Role.ADMIN;
import static com.abdus.hospitalmanagement.entity.type.Role.DOCTOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final OAuthSuccessHandler  oAuthSuccessHandler;
    private final HandlerExceptionResolver handlerExceptionResolver;
//
//    @Value("${GOOGLE_CLIENT_ID}")
//    private String envVar;

//    @PostConstruct
//    public void checkEnv() {
//        if (envVar == null || envVar.isEmpty()) {
//            log.error("CRITICAL: Environment variable is MISSING!");
//        } else {
//            log.info("Environment variable loaded successfully.");
//        }
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                // 1. OAuth2 NEEDS a session to store the authorization request
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/public/**",
                                        "/auth/**",
                                         "/login/**",
                                         "/oauth2/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
//                       .requestMatchers("/admin/create-user").permitAll()
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                                .requestMatchers("/doc/**").hasAnyRole(ADMIN.name(),DOCTOR.name())
                                .anyRequest().authenticated()
                )
                //Jwt filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // OAuth2 Configuration
                .oauth2Login(oAuth2 -> oAuth2
                        .failureHandler(
                                ((request, response, exception) ->{
                                    log.error("OAuth2 error {}", exception.getMessage());
                                    handlerExceptionResolver.resolveException(request,response,null,exception);
        }))
                        .successHandler(oAuthSuccessHandler)
                )
                .exceptionHandling(
                  httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                          .accessDeniedHandler((request,response,e)->
                                  handlerExceptionResolver.resolveException(request,response,null,e))
                );
//                 .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
