package com.phonebook.repository;

import com.phonebook.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void testExistsByMobile() {
        String mobile = "12345678901";

        assertThat(contactRepository.existsByMobile(mobile)).isFalse();

        Contact contact = Contact.builder()
                .name("Test User")
                .mobile(mobile)
                .favorite("N")
                .active("Y")
                .build();

        contactRepository.save(contact);

        assertThat(contactRepository.existsByMobile(mobile)).isTrue();
    }

    @Test
    void testSaveAndFindById() {
        Contact contact = Contact.builder()
                .name("Jane Doe")
                .mobile("09876543210")
                .favorite("Y")
                .active("Y")
                .build();

        Contact saved = contactRepository.save(contact);

        assertThat(saved.getId()).isNotNull();

        Contact found = contactRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getMobile()).isEqualTo("09876543210");
    }
}
