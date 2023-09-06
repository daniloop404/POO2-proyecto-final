package com.isekaiyume.isekaiyume.seguridad;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        // Configura usuarios y roles
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // Permitir recursos estáticos como CSS, JS, etc.
                                .requestMatchers("/listaepisodios/**", "/episodios/**").authenticated() // Requiere autenticación para /listaepisodios y /episodios
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Requiere rol ADMIN para /admin/**
                                .anyRequest().permitAll() // Permite acceso no autenticado a todas las demás solicitudes
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Especifica la página de inicio de sesión personalizada
                                .permitAll()
                                .loginProcessingUrl("/login") // Ruta de procesamiento de inicio de sesión
                                .usernameParameter("nombreUsuario") // Nombre del campo de nombre de usuario en el formulario
                                .passwordParameter("password") // Nombre del campo de contraseña en el formulario
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para la acción de deslogueo
                        .logoutSuccessUrl("/") // Redirige después de deslogueo exitoso
                        .permitAll()
                )
                .httpBasic(withDefaults()) // Configura la autenticación básica con valores predeterminados
                .csrf().disable(); // Deshabilita CSRF
        return http.build();
    }


}