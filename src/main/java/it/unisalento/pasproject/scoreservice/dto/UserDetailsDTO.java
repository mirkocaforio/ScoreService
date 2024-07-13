package it.unisalento.pasproject.scoreservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for User Details.
 * This class is used to encapsulate user details such as email, role, and enabled status,
 * facilitating the transfer of user information between different layers of the application.
 */
@Getter
@Setter
public class UserDetailsDTO {
    private String email; // User's email address
    private String role; // User's role within the application
    private Boolean enabled; // Flag indicating if the user is enabled or disabled

    /**
     * Default constructor for UserDetailsDTO.
     * Initializes a new instance of UserDetailsDTO without setting any properties.
     */
    public UserDetailsDTO() {
    }

    /**
     * Constructs a new UserDetailsDTO with specified email, role, and enabled status.
     * @param email User's email address.
     * @param role User's role within the application.
     * @param enable Flag indicating if the user is enabled or disabled.
     */
    public UserDetailsDTO(String email, String role, Boolean enable) {
        this.email = email;
        this.role = role;
        this.enabled = true;
    }
}