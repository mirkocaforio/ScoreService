package it.unisalento.pasproject.scoreservice.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Holds the security constants used throughout the application.
 * This includes the JWT secret key and predefined roles.
 * The JWT secret key is injected from the application properties.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Getter
public class SecurityConstants {
    public SecurityConstants() {}

    @Value("${secret.key}")
    public String JWT_SECRET; // The secret key used for signing JWT tokens.

    public static final String ROLE_ADMIN = "ADMIN"; // Role for admin users.
    public static final String ROLE_UTENTE = "UTENTE"; // Role for standard users.
    public static final String ROLE_MEMBRO = "MEMBRO"; // Role for member users.
}