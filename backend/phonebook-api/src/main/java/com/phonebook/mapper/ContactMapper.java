package com.phonebook.mapper;

import com.phonebook.dto.ContactRequestDTO;
import com.phonebook.dto.ContactResponseDTO;
import com.phonebook.entity.Contact;

public class ContactMapper {

    public static Contact toEntity(ContactRequestDTO dto) {
        return Contact.builder()
            .name(dto.name())
            .email(dto.email())
            .mobile(dto.mobile())
            .phone(dto.phone())
            .favorite(dto.favorite() == null ? "N" : dto.favorite())
            .active("Y")
            .build();
    }

    public static ContactResponseDTO toDto(Contact entity) {
        return new ContactResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getMobile(),
            entity.getPhone(),
            entity.getFavorite(),
            entity.getActive(),
            entity.getCreatedAt()
        );
    }
}
