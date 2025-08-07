package com.phonebook.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void testBuilderCreatesValidObject() {
        LocalDateTime now = LocalDateTime.now();

        Contact contact = Contact.builder()
            .id(1L)
            .name("Alice")
            .email("alice@example.com")
            .mobile("12345678901")
            .phone("1234567890")
            .favorite("Y")
            .active("N")
            .createdAt(now)
            .build();

        assertEquals(1L, contact.getId());
        assertEquals("Alice", contact.getName());
        assertEquals("alice@example.com", contact.getEmail());
        assertEquals("12345678901", contact.getMobile());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("Y", contact.getFavorite());
        assertEquals("N", contact.getActive());
        assertEquals(now, contact.getCreatedAt());
    }

     @Test
    void testSettersAndGetters() {
        Contact contact = new Contact();

        contact.setId(42L);
        contact.setName("Name");
        contact.setEmail("email@example.com");
        contact.setMobile("1234567890");
        contact.setPhone("0987654321");
        contact.setFavorite("Y");
        contact.setActive("N");
        LocalDateTime now = LocalDateTime.now();
        contact.setCreatedAt(now);

        assertEquals(42L, contact.getId());
        assertEquals("Name", contact.getName());
        assertEquals("email@example.com", contact.getEmail());
        assertEquals("1234567890", contact.getMobile());
        assertEquals("0987654321", contact.getPhone());
        assertEquals("Y", contact.getFavorite());
        assertEquals("N", contact.getActive());
        assertEquals(now, contact.getCreatedAt());
    }

    @Test
    void testPrePersistSetsDefaults() throws Exception {
        Contact contact = new Contact();

        contact.setName("Test");
        contact.setMobile("1234567890");
        contact.setFavorite(null);
        contact.setActive(null);
        contact.setCreatedAt(null);

        var method = Contact.class.getDeclaredMethod("prePersist");
        method.setAccessible(true);
        method.invoke(contact);

        assertNotNull(contact.getCreatedAt());
        assertEquals("N", contact.getFavorite());
        assertEquals("Y", contact.getActive());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        Contact c1 = Contact.builder()
            .id(1L)
            .name("Jane")
            .mobile("1111111111")
            .favorite("Y")
            .active("Y")
            .createdAt(now)
            .build();

        Contact c2 = Contact.builder()
            .id(1L)
            .name("Jane")
            .mobile("1111111111")
            .favorite("Y")
            .active("Y")
            .createdAt(now)
            .build();

        Contact c3 = Contact.builder()
            .id(2L)
            .name("John")
            .mobile("2222222222")
            .favorite("N")
            .active("N")
            .createdAt(now)
            .build();

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }

    @Test
    void testToString() {
        Contact contact = Contact.builder()
            .id(1L)
            .name("Eve")
            .mobile("9999999999")
            .favorite("Y")
            .active("Y")
            .createdAt(LocalDateTime.of(2024, 1, 1, 10, 0))
            .build();

        String result = contact.toString();

        assertTrue(result.contains("Eve"));
        assertTrue(result.contains("9999999999"));
        assertTrue(result.contains("Y"));
        assertTrue(result.contains("2024-01-01T10:00"));
    }
}
