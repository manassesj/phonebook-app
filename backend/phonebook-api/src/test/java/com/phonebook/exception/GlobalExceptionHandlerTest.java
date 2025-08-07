package com.phonebook.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void handleNotFound_returnsNotFoundResponse() {
        EntityNotFoundException ex = new EntityNotFoundException("Contact not found");

        ResponseEntity<ApiError> response = handler.handleNotFound(ex, mockRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().getError());
        assertTrue(response.getBody().getMessages().contains("Contact not found"));
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void handleIllegalArgument_returnsBadRequestResponse() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid parameter");

        ResponseEntity<ApiError> response = handler.handleIllegalArgument(ex, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody().getError());
        assertTrue(response.getBody().getMessages().contains("Invalid parameter"));
    }

    @Test
    void handleValidation_returnsUnprocessableEntityResponse() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("contact", "email", "must be valid");
        FieldError error2 = new FieldError("contact", "mobile", "is required");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiError> response = handler.handleValidation(ex, mockRequest);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Validation error", response.getBody().getError());
        assertTrue(response.getBody().getMessages().contains("email: must be valid"));
        assertTrue(response.getBody().getMessages().contains("mobile: is required"));
    }

    @Test
    void handleGeneric_returnsInternalServerError() {
        Exception ex = new RuntimeException("Unexpected");

        ResponseEntity<ApiError> response = handler.handleGeneric(ex, mockRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getError());
        assertEquals(List.of("Unexpected error. Please contact support."), response.getBody().getMessages());
    }
}
