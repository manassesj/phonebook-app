package com.phonebook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phonebook.dto.ContactRequestDTO;
import com.phonebook.dto.ContactResponseDTO;
import com.phonebook.entity.Contact;
import com.phonebook.mapper.ContactMapper;
import com.phonebook.repository.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContactResponseDTO save(ContactRequestDTO dto) {
        if (repository.existsByMobile(dto.mobile())) {
            logger.warn("Attempt to register duplicate mobile number: {}", dto.mobile());
            throw new IllegalArgumentException("Mobile number is already registered");
        }

        Contact contact = ContactMapper.toEntity(dto);
        Contact saved = repository.save(contact);
        logger.info("Contact created with ID: {}", saved.getId());
        return ContactMapper.toDto(saved);
    }

    public List<ContactResponseDTO> findAll() {
        logger.info("Listing all contacts");
        return repository.findAll()
                         .stream()
                         .map(ContactMapper::toDto)
                         .collect(Collectors.toList());
    }

    public ContactResponseDTO findById(Long id) {
        logger.info("Fetching contact with ID: {}", id);
        Contact contact = repository.findById(id)
            .orElseThrow(() -> {
                logger.error("Contact with ID {} not found", id);
                return new EntityNotFoundException("Contact not found");
            });
        return ContactMapper.toDto(contact);
    }

    @Transactional
    public ContactResponseDTO update(Long id, ContactRequestDTO dto) {
        Contact contact = repository.findById(id)
            .orElseThrow(() -> {
                logger.error("Contact with ID {} not found for update", id);
                return new EntityNotFoundException("Contact not found");
            });

        if (!contact.getMobile().equals(dto.mobile()) && repository.existsByMobile(dto.mobile())) {
            logger.warn("Attempt to update contact ID {} with duplicate mobile number: {}", id, dto.mobile());
            throw new IllegalArgumentException("Mobile number is already registered to another contact");
        }

        contact.setName(dto.name());
        contact.setEmail(dto.email());
        contact.setMobile(dto.mobile());
        contact.setPhone(dto.phone());
        contact.setFavorite(dto.favorite() != null ? dto.favorite() : contact.getFavorite());

        Contact updated = repository.save(contact);
        logger.info("Contact with ID {} updated successfully", updated.getId());
        return ContactMapper.toDto(updated);
    }

    @Transactional
    public ContactResponseDTO toggleFavorite(Long id) {
        Contact contact = repository.findById(id)
            .orElseThrow(() -> {
                logger.error("Contact with ID {} not found to toggle favorite", id);
                return new EntityNotFoundException("Contact not found");
            });

        contact.setFavorite("Y".equals(contact.getFavorite()) ? "N" : "Y");
        Contact updated = repository.save(contact);
        logger.info("Contact with ID {} favorite status toggled to {}", updated.getId(), updated.getFavorite());
        return ContactMapper.toDto(updated);
    }

    @Transactional
    public ContactResponseDTO deactivate(Long id) {
        Contact contact = repository.findById(id)
            .orElseThrow(() -> {
                logger.error("Contact with ID {} not found to deactivate", id);
                return new EntityNotFoundException("Contact not found");
            });

        contact.setActive("N");
        Contact updated = repository.save(contact);
        logger.info("Contact with ID {} has been deactivated", updated.getId());
        return ContactMapper.toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        Contact contact = repository.findById(id)
            .orElseThrow(() -> {
                logger.error("Contact with ID {} not found to delete", id);
                return new EntityNotFoundException("Contact not found");
            });

        repository.delete(contact);
        logger.info("Contact with ID {} deleted", id);
    }
}
