package com.phonebook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactRequestDTO(
    @NotBlank @Size(max = 100) String name,
    @Email @Size(max = 255) String email,
    @NotBlank @Pattern(regexp = "\\d{10,11}") String mobile,
    @Pattern(regexp = "\\d{10}") String phone,
    @Pattern(regexp = "[YN]") String favorite
) {}
