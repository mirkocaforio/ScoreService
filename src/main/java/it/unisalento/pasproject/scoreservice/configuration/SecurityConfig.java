package it.unisalento.pasproject.scoreservice.configuration;

import it.unisalento.pasproject.scoreservice.security.ExceptionFilter;
import it.unisalento.pasproject.scoreservice.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Security configuration for the application.
 * This class is responsible for configuring the security aspects of the application,
 * including authentication, authorization, CORS, CSRF, and custom security filters.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    /**
     * Configures the security filter chain for the application.
     * This method sets up the security filter chain that applies various security configurations
     * such as disabling CORS and CSRF, and registering custom filters for JWT authentication and exception handling.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object that contains the configured security filters.
     * @throws Exception If an error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CORS
        http.cors(AbstractHttpConfigurer::disable);

        // Disable CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // Register the ExceptionFilter before the LogoutFilter to handle exceptions
        http.addFilterBefore(exceptionFilter(), LogoutFilter.class);

        // Register the JwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter
        // to add JWT based authentication
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Creates and returns an AuthenticationManager bean.
     * The AuthenticationManager is responsible for processing authentication requests.
     *
     * @param authenticationConfiguration The authentication configuration used to build the AuthenticationManager.
     * @return The AuthenticationManager bean.
     * @throws Exception If an error occurs during the creation of the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates and returns a JwtAuthenticationFilter bean.
     * This filter intercepts authentication requests to perform JWT based authentication.
     *
     * @return The JwtAuthenticationFilter bean.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Creates and returns an ExceptionFilter bean.
     * This filter is used to handle exceptions globally across the application.
     *
     * @return The ExceptionFilter bean.
     */
    @Bean
    public ExceptionFilter exceptionFilter() {
        return new ExceptionFilter();
    }
}