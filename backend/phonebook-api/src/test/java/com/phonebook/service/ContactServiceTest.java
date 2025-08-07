package com.phonebook.service;

import com.phonebook.dto.ContactRequestDTO;
import com.phonebook.dto.ContactResponseDTO;
import com.phonebook.entity.Contact;
import com.phonebook.mapper.ContactMapper;
import com.phonebook.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    private ContactRepository repository;
    private ContactService service;

    @BeforeEach
    void setUp() {
        repository = mock(ContactRepository.class);
        service = new ContactService(repository);
    }

    @Test
    void save_shouldSaveContact_whenMobileNotExists() {
        ContactRequestDTO dto = new ContactRequestDTO("Name", "email@example.com", "12345678901", "1234567890", "Y");
        when(repository.existsByMobile(dto.mobile())).thenReturn(false);

        Contact contact = ContactMapper.toEntity(dto);
        Contact savedContact = Contact.builder()
                .id(1L)
                .name(contact.getName())
                .email(contact.getEmail())
                .mobile(contact.getMobile())
                .phone(contact.getPhone())
                .favorite(contact.getFavorite())
                .active(contact.getActive())
                .build();

        when(repository.save(any(Contact.class))).thenReturn(savedContact);

        ContactResponseDTO response = service.save(dto);

        assertThat(response.id()).isEqualTo(1L);
        verify(repository).existsByMobile(dto.mobile());
        verify(repository).save(any(Contact.class));
    }

    @Test
    void save_shouldThrowException_whenMobileExists() {
        ContactRequestDTO dto = new ContactRequestDTO("Name", "email@example.com", "12345678901", "1234567890", "Y");
        when(repository.existsByMobile(dto.mobile())).thenReturn(true);

        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Mobile number is already registered");

        verify(repository, never()).save(any());
    }

    @Test
    void findAll_shouldReturnList() {
        Contact c1 = Contact.builder().id(1L).name("A").mobile("12345678901").favorite("Y").active("Y").build();
        Contact c2 = Contact.builder().id(2L).name("B").mobile("12345678902").favorite("N").active("Y").build();
        when(repository.findAll()).thenReturn(List.of(c1, c2));

        List<ContactResponseDTO> result = service.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("A");
        assertThat(result.get(1).name()).isEqualTo("B");
    }

    @Test
    void findById_shouldReturnContact_whenFound() {
        Contact contact = Contact.builder().id(1L).name("A").mobile("12345678901").favorite("Y").active("Y").build();
        when(repository.findById(1L)).thenReturn(Optional.of(contact));

        ContactResponseDTO result = service.findById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("A");
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Contact not found");
    }

    @Test
    void update_shouldUpdateContact_whenValid() {
        Contact existing = Contact.builder()
                .id(1L)
                .name("Old")
                .email("old@example.com")
                .mobile("12345678901")
                .phone("1111111111")
                .favorite("N")
                .active("Y")
                .build();

        ContactRequestDTO dto = new ContactRequestDTO("New", "new@example.com", "12345678902", "2222222222", "Y");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByMobile(dto.mobile())).thenReturn(false);
        when(repository.save(any(Contact.class))).thenAnswer(i -> i.getArgument(0));

        ContactResponseDTO updated = service.update(1L, dto);

        assertThat(updated.name()).isEqualTo("New");
        assertThat(updated.mobile()).isEqualTo("12345678902");
        assertThat(updated.favorite()).isEqualTo("Y");
    }

    @Test
    void update_shouldThrow_whenMobileDuplicate() {
        Contact existing = Contact.builder()
                .id(1L)
                .mobile("12345678901")
                .build();

        ContactRequestDTO dto = new ContactRequestDTO("Name", "email@example.com", "99999999999", null, "Y");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByMobile(dto.mobile())).thenReturn(true);

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Mobile number is already registered to another contact");
    }

    @Test
    void toggleFavorite_shouldToggleFavorite() {
        Contact contact = Contact.builder()
                .id(1L)
                .favorite("Y")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(contact));
        when(repository.save(any(Contact.class))).thenAnswer(i -> i.getArgument(0));

        ContactResponseDTO result = service.toggleFavorite(1L);

        assertThat(result.favorite()).isEqualTo("N");

        contact.setFavorite("N");
        ContactResponseDTO result2 = service.toggleFavorite(1L);
        assertThat(result2.favorite()).isEqualTo("Y");
    }

    @Test
    void deactivate_shouldSetActiveToN() {
        Contact contact = Contact.builder()
                .id(1L)
                .active("Y")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(contact));
        when(repository.save(any(Contact.class))).thenAnswer(i -> i.getArgument(0));

        ContactResponseDTO result = service.deactivate(1L);

        assertThat(result.active()).isEqualTo("N");
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        Contact contact = Contact.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(contact));
        doNothing().when(repository).delete(contact);

        service.delete(1L);

        verify(repository).delete(contact);
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Contact not found");
    }
}
