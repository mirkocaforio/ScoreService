package it.unisalento.pasproject.scoreservice.exceptions.global;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Global exception handler for the application.
 * This class provides centralized exception handling across all @RequestMapping methods through @ExceptionHandler methods.
 */
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensures this advice is applied before any other exception handling components.
@ControllerAdvice // Marks this class as a global exception handler.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions of type CustomErrorException.
     * Constructs a ResponseEntity with a CustomErrorResponse body including details from the exception.
     *
     * @param ex The caught CustomErrorException.
     * @return ResponseEntity containing the custom error response.
     */
    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<CustomErrorResponse> handleTransactionNotFoundException(RuntimeException ex) {
        CustomErrorException exception = (CustomErrorException) ex;
        return ResponseEntity.status(exception.getErrorResponse().getStatus()).body(exception.getErrorResponse());
    }

    /**
     * Handles AccessDeniedException, typically thrown when an authentication request is rejected due to insufficient permissions.
     * Constructs a ResponseEntity with a CustomErrorResponse body detailing the access denied error.
     *
     * @param ex The caught AccessDeniedException.
     * @return ResponseEntity containing the custom error response.
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().toString())
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    /**
     * General exception handler that overrides the default Spring MVC exception handler.
     * This method constructs a ResponseEntity with a CustomErrorResponse body for any unhandled exceptions.
     *
     * @param ex The exception that was not handled.
     * @param body The body for the response.
     * @param headers The headers for the response.
     * @param statusCode The HTTP status code that should be returned.
     * @param request The current web request.
     * @return ResponseEntity containing the custom error response.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .traceId(UUID.randomUUID().toString())
                .timestamp(OffsetDateTime.now().toString())
                .status(HttpStatus.resolve(statusCode.value()))
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}