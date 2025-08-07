package com.phonebook.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorTest {

    @Test
    void testApiErrorInitialization() {
        int status = 404;
        String error = "Not Found";
        List<String> messages = List.of("Resource not found", "Check the ID");
        String path = "/api/contacts/1";

        ApiError apiError = new ApiError(status, error, messages, path);

        assertEquals(status, apiError.getStatus());
        assertEquals(error, apiError.getError());
        assertEquals(messages, apiError.getMessages());
        assertEquals(path, apiError.getPath());
        assertNotNull(apiError.getTimestamp());

    }
}
