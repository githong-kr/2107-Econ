package com.bale.econ.springboot.config.auth;

import com.bale.econ.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/message/**", "/api/v1/**").hasRole(Role.ADMIN.name())
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .antMatchers("/**", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                .and()
                    .sessionManagement()
                        .maximumSessions(10)
                        .expiredUrl("/")
                        .maxSessionsPreventsLogin(true)
                        .and()
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
