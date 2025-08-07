package com.phonebook.mapper;

import com.phonebook.dto.ContactRequestDTO;
import com.phonebook.dto.ContactResponseDTO;
import com.phonebook.entity.Contact;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    @Test
    void toEntity_shouldMapDtoToEntityWithDefaults() {
        ContactRequestDTO dto = new ContactRequestDTO(
            "Alice",
            "alice@example.com",
            "12345678901",
            "1234567890",
            null 
        );

        Contact entity = ContactMapper.toEntity(dto);

        assertEquals("Alice", entity.getName());
        assertEquals("alice@example.com", entity.getEmail());
        assertEquals("12345678901", entity.getMobile());
        assertEquals("1234567890", entity.getPhone());
        assertEquals("N", entity.getFavorite(), "favorite should default to 'N'");
        assertEquals("Y", entity.getActive(), "active should default to 'Y'");
    }

    @Test
    void toDto_shouldMapEntityToDto() {
        LocalDateTime now = LocalDateTime.now();

        Contact entity = Contact.builder()
            .id(10L)
            .name("Bob")
            .email("bob@example.com")
            .mobile("09876543210")
            .phone("0987654321")
            .favorite("Y")
            .active("N")
            .createdAt(now)
            .build();

        ContactResponseDTO dto = ContactMapper.toDto(entity);

        assertEquals(10L, dto.id());
        assertEquals("Bob", dto.name());
        assertEquals("bob@example.com", dto.email());
        assertEquals("09876543210", dto.mobile());
        assertEquals("0987654321", dto.phone());
        assertEquals("Y", dto.favorite());
        assertEquals("N", dto.active());
        assertEquals(now, dto.createdAt());
    }
}
