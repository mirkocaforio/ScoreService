package it.unisalento.pasproject.scoreservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Service class for JWT (JSON Web Token) utilities.
 * Provides functionality for extracting information from JWT tokens, such as username, role, and expiration,
 * and for validating JWT tokens against user details and roles.
 */
@Service
public class JwtUtilities {
    private final Key key;

    /**
     * Constructs a JwtUtilities instance with a security key derived from the provided security constants.
     *
     * @param securityConstants The security constants containing the JWT secret.
     */
    @Autowired
    public JwtUtilities(SecurityConstants securityConstants) {
        key = Keys.hmacShaKeyFor(securityConstants.getJWT_SECRET().getBytes());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtilities.class);

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token The JWT token.
     * @return The role extracted from the token.
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic method to extract a specific claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver The function to apply to the claims for extracting the desired information.
     * @param <T>            The type of the extracted information.
     * @return The extracted information.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token.
     * @return {@code true} if the token has expired, otherwise {@code false}.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the JWT token against user details and role.
     * Checks if the username and role in the token match the provided user details and role,
     * and also checks if the token has not expired.
     *
     * @param token       The JWT token.
     * @param userDetails The user details to validate against.
     * @param role        The role to validate against.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public Boolean validateToken(String token, UserDetails userDetails, String role) {
        final String username = extractUsername(token);
        final String tokenRole = extractRole(token);
        return (username.equals(userDetails.getUsername()) && tokenRole.equalsIgnoreCase(role) && !isTokenExpired(token));
    }
}