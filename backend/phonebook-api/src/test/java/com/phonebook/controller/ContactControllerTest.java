package com.phonebook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebook.dto.ContactRequestDTO;
import com.phonebook.dto.ContactResponseDTO;
import com.phonebook.service.ContactService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactRequestDTO validRequest() {
        return new ContactRequestDTO("Alice", "alice@mail.com", "11999999999", "1133334444", "Y");
    }

    private ContactResponseDTO validResponse() {
        return new ContactResponseDTO(1L, "Alice", "alice@mail.com", "11999999999", "1133334444", "Y", "Y", LocalDateTime.now());
    }

    @Test
    @DisplayName("POST /api/contacts should return 201 with created contact")
    void createContact_shouldReturnCreated() throws Exception {
        Mockito.when(service.save(any())).thenReturn(validResponse());

        mockMvc.perform(post("/api/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/contacts should return all contacts")
    void listAll_shouldReturnList() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(validResponse()));

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/contacts/{id} should return one contact")
    void getById_shouldReturnContact() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(validResponse());

        mockMvc.perform(get("/api/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("PUT /api/contacts/{id} should update and return contact")
    void updateContact_shouldReturnUpdated() throws Exception {
        Mockito.when(service.update(eq(1L), any())).thenReturn(validResponse());

        mockMvc.perform(put("/api/contacts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("PATCH /api/contacts/{id}/deactivate should deactivate contact")
    void deactivateContact_shouldReturnUpdated() throws Exception {
        Mockito.when(service.deactivate(1L)).thenReturn(validResponse());

        mockMvc.perform(patch("/api/contacts/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("PATCH /api/contacts/{id}/favorite should toggle favorite")
    void toggleFavorite_shouldReturnUpdated() throws Exception {
        Mockito.when(service.toggleFavorite(1L)).thenReturn(validResponse());

        mockMvc.perform(patch("/api/contacts/1/favorite"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favorite").value("Y"));
    }

    @Test
    @DisplayName("DELETE /api/contacts/{id} should return 204")
    void deleteContact_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/contacts/1"))
                .andExpect(status().isNoContent());
    }
}
