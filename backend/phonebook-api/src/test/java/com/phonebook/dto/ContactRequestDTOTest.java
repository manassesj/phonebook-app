package com.phonebook.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContactRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassWithValidData() {
        ContactRequestDTO dto = new ContactRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "11999999999",
                "1133334444",
                "Y"
        );

        Set<ConstraintViolation<ContactRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        ContactRequestDTO dto = new ContactRequestDTO(
                " ",
                "john.doe@example.com",
                "11999999999",
                "1133334444",
                "Y"
        );

        assertHasViolation(dto, "name");
    }

    @Test
    void shouldFailWhenEmailIsInvalid() {
        ContactRequestDTO dto = new ContactRequestDTO(
                "John Doe",
                "invalid-email",
                "11999999999",
                "1133334444",
                "Y"
        );

        assertHasViolation(dto, "email");
    }

    @Test
    void shouldFailWhenMobileIsInvalid() {
        ContactRequestDTO dto = new ContactRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "12345",  // Invalid mobile
                "1133334444",
                "Y"
        );

        assertHasViolation(dto, "mobile");
    }

    @Test
    void shouldFailWhenPhoneIsInvalid() {
        ContactRequestDTO dto = new ContactRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "11999999999",
                "123", // Invalid phone
                "Y"
        );

        assertHasViolation(dto, "phone");
    }

    @Test
    void shouldFailWhenFavoriteIsInvalid() {
        ContactRequestDTO dto = new ContactRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "11999999999",
                "1133334444",
                "Z" // Invalid favorite
        );

        assertHasViolation(dto, "favorite");
    }

    // Utility method to check violation for a specific field
    private void assertHasViolation(ContactRequestDTO dto, String fieldName) {
        Set<ConstraintViolation<ContactRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Expected validation error on: " + fieldName);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(fieldName)),
                "Expected violation on field: " + fieldName);
    }
}
