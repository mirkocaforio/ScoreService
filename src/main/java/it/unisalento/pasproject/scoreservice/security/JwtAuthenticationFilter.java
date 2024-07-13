package it.unisalento.pasproject.scoreservice.security;

import it.unisalento.pasproject.scoreservice.dto.UserDetailsDTO;
import it.unisalento.pasproject.scoreservice.exceptions.AccessDeniedException;
import it.unisalento.pasproject.scoreservice.exceptions.UserNotAuthorizedException;
import it.unisalento.pasproject.scoreservice.service.UserCheckService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter for JWT (JSON Web Token) authentication that extends {@link OncePerRequestFilter}.
 * This filter intercepts all HTTP requests to authenticate users based on the JWT token provided in the Authorization header.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private UserCheckService userCheckService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * Processes each HTTP request, extracting and validating the JWT token from the Authorization header.
     * If the token is valid, it authenticates the user and sets the security context.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param chain       the filter chain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        String role = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtilities.extractUsername(jwt);
                role = jwtUtilities.extractRole(jwt);
            } else {
                throw new AccessDeniedException("Invalid token");
            }
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid token: " + e.getMessage());
        }

        if (username != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsDTO user = this.userCheckService.loadUserByUsername(username);

            String userEmail;
            String userRole;
            boolean userEnabled;

            if (user == null) {
                LOGGER.info("User not found in CQRS, assuming user is the email from the token");
                userEmail = username;
                userRole = role;
                userEnabled = true;
            } else {
                userEmail = user.getEmail();
                userRole = user.getRole();
                userEnabled = user.getEnabled();
            }

            UserDetails userDetails = User.builder()
                    .username(userEmail) // Assume email is username
                    .password("") // Password field is not used in JWT authentication
                    .authorities(userRole) // Set roles or authorities from the UserDetailsDTO
                    .build();

            if (jwtUtilities.validateToken(jwt, userDetails, role) && userCheckService.isEnable(userEnabled)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new UserNotAuthorizedException("User not authorized");
            }
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new AccessDeniedException("No authentication found");
        }

        chain.doFilter(request, response);
    }

}