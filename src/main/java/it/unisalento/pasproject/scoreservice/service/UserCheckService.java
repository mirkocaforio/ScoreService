package it.unisalento.pasproject.scoreservice.service;

import it.unisalento.pasproject.scoreservice.business.exchanger.MessageExchangeStrategy;
import it.unisalento.pasproject.scoreservice.business.exchanger.MessageExchanger;
import it.unisalento.pasproject.scoreservice.dto.UserDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static it.unisalento.pasproject.scoreservice.security.SecurityConstants.ROLE_ADMIN;

/**
 * Service class for user authentication and authorization checks.
 * Utilizes a message exchanger to communicate with external services for user details retrieval.
 */
@Service
public class UserCheckService {

    private final MessageExchanger messageExchanger;

    @Value("${rabbitmq.exchange.security.name}")
    private String securityExchange;

    @Value("${rabbitmq.routing.security.key}")
    private String securityRequestRoutingKey;

    /**
     * Constructs a UserCheckService with a specified message exchanger and exchange strategy.
     * @param messageExchanger The message exchanger component for communication.
     * @param messageExchangeStrategy The strategy to use for message exchange.
     */
    @Autowired
    public UserCheckService(MessageExchanger messageExchanger, @Qualifier("RabbitMQExchange") MessageExchangeStrategy messageExchangeStrategy) {
        this.messageExchanger = messageExchanger;
        this.messageExchanger.setStrategy(messageExchangeStrategy);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCheckService.class);

    /**
     * Loads user details by username (email) through an external service call.
     * @param email The email of the user to load.
     * @return UserDetailsDTO containing user details.
     * @throws UsernameNotFoundException if the user cannot be found.
     */
    public UserDetailsDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsDTO user = null;
        try {
            user = messageExchanger.exchangeMessage(email, securityRequestRoutingKey, securityExchange, UserDetailsDTO.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        if(user != null) {
            LOGGER.info(String.format("User %s found with role: %s and enabled %s", user.getEmail(), user.getRole(), user.getEnabled()));
        }

        return user;
    }

    /**
     * Checks if a given boolean value representing user's enabled status is true.
     * @param enable The boolean value to check.
     * @return The same boolean value passed as parameter.
     */
    public Boolean isEnable(Boolean enable) {
        return enable;
    }

    /**
     * Checks if the current authenticated user matches the given email.
     * @param email The email to check against the current authenticated user.
     * @return true if the current user's email matches the given email, false otherwise.
     */
    public Boolean isCorrectUser(String email){
        return email.equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Checks if the current authenticated user has an administrator role.
     * @return true if the current user is an administrator, false otherwise.
     */
    public Boolean isAdministrator(){
        String currentRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return currentRole.equalsIgnoreCase(ROLE_ADMIN);
    }
}