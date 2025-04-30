package br.com.miqueias.recruta_auth.config;

import br.com.miqueias.recruta_auth.filter.AccessTokenFilter;
import br.com.miqueias.recruta_auth.security.JWTService;
import br.com.miqueias.recruta_auth.security.UsuarioDetalheServiceImpl;
import br.com.miqueias.recruta_auth.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTService jwtService;
    private final JWTUtils jwtUtils;
    private final UsuarioDetalheServiceImpl usuarioDetalheService;

    @Autowired
    public SecurityConfig(JWTService jwtService, JWTUtils jwtUtils, UsuarioDetalheServiceImpl usuarioDetalheService) {
        this.jwtService = jwtService;
        this.jwtUtils = jwtUtils;
        this.usuarioDetalheService = usuarioDetalheService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers(HttpMethod.POST, "/api/v1/usuario").permitAll()
                        .antMatchers("/api/v1/auth/login", "/api/v1/auth/refresh").permitAll()
                        .antMatchers("/api/v1/auth/papel").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AccessTokenFilter(jwtService,jwtUtils, usuarioDetalheService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
