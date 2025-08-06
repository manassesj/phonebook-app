package com.phonebook.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contact", schema = "public")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can be at most 100 characters")
    @Column(name = "contact_name", nullable = false, length = 100)
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email can be at most 255 characters")
    @Column(name = "contact_email", length = 255)
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10,11}", message = "Mobile must have 10 or 11 numeric digits")
    @Column(name = "contact_mobile", nullable = false, length = 11, unique = true)
    private String mobile;

    @Pattern(regexp = "\\d{10}", message = "Phone must have 10 numeric digits")
    @Column(name = "contact_phone", length = 10)
    private String phone;

    @NotNull
    @Pattern(regexp = "[YN]", message = "Favorite must be 'Y' or 'N'")
    @Column(name = "contact_is_favorite", nullable = false, length = 1)
    private String favorite;

    @NotNull
    @Pattern(regexp = "[YN]", message = "Active must be 'Y' or 'N'")
    @Column(name = "contact_is_active", nullable = false, length = 1)
    private String active;

    @PastOrPresent
    @Column(name = "contact_created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (favorite == null) {
            favorite = "N";
        }
        if (active == null) {
            active = "Y";
        }
    }
}
