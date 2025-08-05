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
@Table(name = "contato", schema = "desafio")
public class Contato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contato_id")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome pode ter no máximo 100 caracteres")
    @Column(name = "contato_nome", nullable = false, length = 100)
    private String nome;

    
    @Email(message = "Email deve ser válido")
    @Size(max = 255, message = "Email pode ter no máximo 255 caracteres")
    @Column(name = "contato_email", length = 255)
    private String email;

    @NotBlank(message = "Celular é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Celular deve conter 10 ou 11 dígitos numéricos")
    @Column(name = "contato_celular", nullable = false, length = 11, unique = true)
    private String celular;

    @Pattern(regexp = "\\d{10}", message = "Telefone deve conter 10 dígitos numéricos")
    @Column(name = "contato_telefone", length = 10)
    private String telefone;

    @NotNull
    @Pattern(regexp = "[SN]", message = "Favorito deve ser 'S' ou 'N'")
    @Column(name = "contato_sn_favorito", nullable = false, length = 1)
    private String favorito;

    @NotNull
    @Pattern(regexp = "[SN]", message = "Ativo deve ser 'S' ou 'N'")
    @Column(name = "contato_sn_ativo", nullable = false, length = 1)
    private String ativo;

    @PastOrPresent
    @Column(name = "contato_dh_cad", nullable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    private void prePersist() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
        if (favorito == null) {
            favorito = "N";
        }
        if (ativo == null) {
            ativo = "S";
        }
    }
}
