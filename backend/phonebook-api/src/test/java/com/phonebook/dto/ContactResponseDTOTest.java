package com.phonebook.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContactResponseDTOTest {

    @Test
    void testRecordFieldsAreSetCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        ContactResponseDTO dto = new ContactResponseDTO(
            1L,
            "John Doe",
            "john@example.com",
            "1234567890",
            "0987654321",
            "Y",
            "Y",
            now
        );

        assertEquals(1L, dto.id());
        assertEquals("John Doe", dto.name());
        assertEquals("john@example.com", dto.email());
        assertEquals("1234567890", dto.mobile());
        assertEquals("0987654321", dto.phone());
        assertEquals("Y", dto.favorite());
        assertEquals("Y", dto.active());
        assertEquals(now, dto.createdAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        ContactResponseDTO dto1 = new ContactResponseDTO(1L, "John", "john@example.com", "123", "321", "Y", "Y", now);
        ContactResponseDTO dto2 = new ContactResponseDTO(1L, "John", "john@example.com", "123", "321", "Y", "Y", now);
        ContactResponseDTO dto3 = new ContactResponseDTO(2L, "Jane", "jane@example.com", "456", "654", "N", "Y", now);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0);

        ContactResponseDTO dto = new ContactResponseDTO(
            1L, "John", "john@example.com", "123", "321", "Y", "Y", now
        );

        String expected = "ContactResponseDTO[id=1, name=John, email=john@example.com, mobile=123, phone=321, favorite=Y, active=Y, createdAt=2024-01-01T12:00]";
        assertEquals(expected, dto.toString());
    }
}
