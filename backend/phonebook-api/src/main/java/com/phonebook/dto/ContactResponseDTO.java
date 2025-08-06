package com.phonebook.dto;

import java.time.LocalDateTime;

public record ContactResponseDTO(
    Long id,
    String name,
    String email,
    String mobile,
    String phone,
    String favorite,
    String active,
    LocalDateTime createdAt
) {}
